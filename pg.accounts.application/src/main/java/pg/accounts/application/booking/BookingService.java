package pg.accounts.application.booking;

import lombok.NonNull;

import java.math.BigDecimal;

public interface BookingService {
    String createBooking(@NonNull String accountId, @NonNull String currency, @NonNull BigDecimal amount);

    void realizeAccountBooking(@NonNull String accountId, @NonNull String bookingId);

    void releaseAccountBooking(@NonNull String accountId, @NonNull String bookingId);
}
