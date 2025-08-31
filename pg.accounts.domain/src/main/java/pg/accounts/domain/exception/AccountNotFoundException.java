package pg.accounts.domain.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(final String accountId) {
        super(String.format("Account with id %s not found", accountId));
    }
}
