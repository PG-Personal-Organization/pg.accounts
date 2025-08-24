package pg.accounts.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
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

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(balanceId, that.balanceId) && Objects.equals(account, that.account) && Objects.equals(currency, that.currency)
                && Objects.equals(balance, that.balance) && Objects.equals(bookedBalance, that.bookedBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, account, currency, balance, bookedBalance);
    }
}
