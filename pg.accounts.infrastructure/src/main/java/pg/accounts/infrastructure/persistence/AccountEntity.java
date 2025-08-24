package pg.accounts.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountEntity {
    @Id
    private String accountId;

    private String accountNumber;

    private String name;

    private Boolean multiCurrency;

    /**
     * Single currency account usage only.
     */
    private String currency;

    /**
     * Multi-currency account usage only.
     */
    private List<String> currencies;

    private Boolean blocked;

    @OneToMany(mappedBy = "account", fetch = jakarta.persistence.FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<AccountBalance> balances;

    @OneToMany(mappedBy = "account", fetch = jakarta.persistence.FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<AccountPermission> accountPermissions;

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(name, that.name)
                && Objects.equals(multiCurrency, that.multiCurrency) && Objects.equals(currency, that.currency) && Objects.equals(currencies, that.currencies)
                && Objects.equals(blocked, that.blocked) && Objects.equals(balances, that.balances) && Objects.equals(accountPermissions, that.accountPermissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountNumber, name, multiCurrency, currency, currencies, blocked, balances, accountPermissions);
    }
}
