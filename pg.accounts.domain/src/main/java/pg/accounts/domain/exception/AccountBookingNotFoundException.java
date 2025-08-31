package pg.accounts.domain.exception;

public class AccountBookingNotFoundException extends RuntimeException {
    public AccountBookingNotFoundException(final String bookingId, final String accountId) {
        super(String.format("Account booking with id %s not found for account with id %s", bookingId, accountId));
    }
}
