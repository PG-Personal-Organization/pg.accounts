package pg.accounts.infrastructure.persistence.account.balances;

import jakarta.persistence.*;
import lombok.*;
import pg.accounts.infrastructure.persistence.account.AccountEntity;
import pg.accounts.infrastructure.persistence.account.bookings.AccountBalanceBooking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity(name = "accounts_balances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountBalance {
    @Id
    private String balanceId;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "accountId")
    @ToString.Exclude
    private AccountEntity account;

    private String currency;

    private BigDecimal balance;

    private BigDecimal bookedBalance;

    @OneToMany(mappedBy = "accountBalance", fetch = jakarta.persistence.FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<AccountBalanceBooking> bookedBalances;

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(balanceId, that.balanceId) && Objects.equals(account, that.account) && Objects.equals(currency, that.currency)
                && Objects.equals(balance, that.balance) && Objects.equals(bookedBalance, that.bookedBalance) && Objects.equals(bookedBalances, that.bookedBalances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, account, currency, balance, bookedBalance, bookedBalances);
    }
}
