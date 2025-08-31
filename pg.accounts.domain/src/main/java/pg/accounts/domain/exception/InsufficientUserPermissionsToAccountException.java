package pg.accounts.domain.exception;

import pg.accounts.domain.AccountViewUsage;

public class InsufficientUserPermissionsToAccountException extends RuntimeException {
    public InsufficientUserPermissionsToAccountException(final String userId, final String accountId, final AccountViewUsage forUsage) {
        super(String.format("Insufficient permissions for usage: %s for user with id %s to account with id %s", forUsage.name(), userId, accountId));
    }
}
