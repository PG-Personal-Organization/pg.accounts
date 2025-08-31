package pg.accounts.infrastructure.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pg.accounts.api.AccountModel;
import pg.accounts.application.AccountPermissionService;
import pg.accounts.application.AccountsService;
import pg.accounts.domain.AccountViewUsage;
import pg.context.auth.api.context.provider.ContextProvider;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class ContextAccountPermissionServiceImpl implements AccountPermissionService {
    private final ContextProvider contextProvider;
    private final AccountsService accountsService;

    @Override
    public boolean hasPermission(final @NonNull String accountId, final @NonNull AccountViewUsage permission) {
        var account = accountsService.getAccountById(accountId);
        return hasPermissionsToAccount(account, permission);
    }

    @Override
    public boolean hasPermissionsToAccount(final @NonNull AccountModel account, final @NonNull AccountViewUsage permission) {
        var userContext = contextProvider.tryToGetUserContext().orElseThrow();
        return Optional.ofNullable(account.getRequiredPermissions().get(permission))
                .map(userContext::hasAllPermissions)
                .orElse(false);
    }
}
