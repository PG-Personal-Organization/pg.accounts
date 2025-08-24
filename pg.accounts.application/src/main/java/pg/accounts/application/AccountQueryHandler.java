package pg.accounts.application;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pg.accounts.api.model.AccountQuery;
import pg.accounts.api.model.AccountView;
import pg.accounts.api.model.AccountViewResponse;
import pg.accounts.domain.AccountViewUsage;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.context.auth.domain.context.UserContext;
import pg.lib.cqrs.query.QueryHandler;

import java.util.Optional;

@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler<AccountQuery, AccountViewResponse> {
    private final AccountsService accountsService;
    private final ContextProvider contextProvider;

    @Override
    public AccountViewResponse handle(final AccountQuery query) {
        var account = accountsService.getAccountByNumber(query.getAccountNumber());

        if (account == null) {
            return AccountViewResponse.builder().found(Boolean.FALSE).build();
        }

        if (!hasPermissionsToAccount(account, query.getViewUsage())) {
            return AccountViewResponse.builder().found(Boolean.TRUE).hasAccess(Boolean.FALSE).build();
        }

        return AccountViewResponse.builder().found(Boolean.TRUE).hasAccess(Boolean.TRUE).account(account).build();
    }

    @SneakyThrows
    private boolean hasPermissionsToAccount(final AccountModel account, final AccountViewUsage usage) {
        var userContext = (UserContext) contextProvider.tryToGetUserContext().orElseThrow();
        return Optional.ofNullable(account.getRequiredPermissions().get(usage))
                .map(userContext::hasAllPermissions)
                .orElse(false);
    }
}
