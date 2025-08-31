package pg.accounts.domain.exception;

public class UserNotAuthorizedToReleaseBookingBalanceException extends RuntimeException {
    public UserNotAuthorizedToReleaseBookingBalanceException(final String userId, final String bookingId) {
        super(String.format("User with id %s is not authorized to realize booking balance with id %s", userId, bookingId));
    }
}
