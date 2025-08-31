package pg.accounts.application;

import lombok.RequiredArgsConstructor;
import pg.accounts.api.AccountQuery;
import pg.accounts.api.AccountViewResponse;
import pg.lib.cqrs.query.QueryHandler;

@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler<AccountQuery, AccountViewResponse> {
    private final AccountsService accountsService;
    private final AccountPermissionService accountPermissionService;

    @Override
    public AccountViewResponse handle(final AccountQuery query) {
        var account = accountsService.getAccountByNumber(query.getAccountNumber());

        if (account == null) {
            return AccountViewResponse.builder().found(Boolean.FALSE).build();
        }

        if (!accountPermissionService.hasPermissionsToAccount(account, query.getViewUsage())) {
            return AccountViewResponse.builder().found(Boolean.TRUE).hasAccess(Boolean.FALSE).build();
        }

        return AccountViewResponse.builder().found(Boolean.TRUE).hasAccess(Boolean.TRUE).account(account).build();
    }
}
