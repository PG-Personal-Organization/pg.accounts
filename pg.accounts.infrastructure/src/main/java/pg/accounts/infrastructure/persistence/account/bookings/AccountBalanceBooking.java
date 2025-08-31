package pg.accounts.infrastructure.persistence.account.bookings;

import jakarta.persistence.*;
import lombok.*;
import pg.accounts.domain.AccountBalanceBookingState;
import pg.accounts.infrastructure.persistence.account.balances.AccountBalance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "accounts_balances_bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountBalanceBooking {
    @Id
    private String bookingId;

    @Enumerated(EnumType.STRING)
    private AccountBalanceBookingState state;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "accountBalanceId")
    @ToString.Exclude
    private AccountBalance accountBalance;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    private LocalDateTime processedDate;

    private LocalDateTime closedDate;

    @Column(nullable = false)
    private String bookedBy;

    @Column
    private String processedBy;

    @Column
    private String closedBy;

    public static AccountBalanceBooking createNew(final @NonNull String bookingId,
                                                  final @NonNull AccountBalance accountBalance,
                                                  final @NonNull BigDecimal amount,
                                                  final @NonNull String currency,
                                                  final @NonNull String bookedBy) {
        return AccountBalanceBooking.builder()
                .bookingId(bookingId)
                .state(AccountBalanceBookingState.NEW)
                .accountBalance(accountBalance)
                .amount(amount)
                .currency(currency)
                .bookedBy(bookedBy)
                .bookingDate(LocalDateTime.now())
                .build();
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public void processBooking(final @NonNull String processedBy) {
        this.processedDate = LocalDateTime.now();
        this.state = AccountBalanceBookingState.PROCESSED;
        this.processedBy = processedBy;
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public void releaseBooking(final @NonNull String closedBy) {
        this.closedDate = LocalDateTime.now();
        this.state = AccountBalanceBookingState.RELEASED;
        this.closedBy = closedBy;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountBalanceBooking that = (AccountBalanceBooking) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(accountBalance, that.accountBalance) && Objects.equals(amount, that.amount)
                && Objects.equals(currency, that.currency) && Objects.equals(bookingDate, that.bookingDate) && Objects.equals(processedDate, that.processedDate)
                && Objects.equals(closedDate, that.closedDate) && Objects.equals(bookedBy, that.bookedBy) && Objects.equals(processedBy, that.processedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, accountBalance, amount, currency, bookingDate, processedDate, closedDate, bookedBy, processedBy);
    }
}
