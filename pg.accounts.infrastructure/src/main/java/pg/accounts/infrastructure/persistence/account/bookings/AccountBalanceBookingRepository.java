package pg.accounts.infrastructure.persistence.account.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountBalanceBookingRepository extends JpaRepository<AccountBalanceBooking, String> {
    @Query("""
            SELECT b FROM accounts_balances_bookings b
                        WHERE b.accountBalance.account.accountId = :accountId
                        AND b.bookingId = :bookingId
            """)
    Optional<AccountBalanceBooking> findBooking(String accountId, String bookingId);
}
