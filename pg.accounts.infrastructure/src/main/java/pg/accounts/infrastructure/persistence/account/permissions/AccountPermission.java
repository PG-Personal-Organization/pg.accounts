package pg.accounts.infrastructure.persistence.account.permissions;

import jakarta.persistence.*;
import lombok.*;
import pg.accounts.domain.AccountViewUsage;
import pg.accounts.infrastructure.persistence.account.AccountEntity;

import java.util.Objects;
import java.util.Set;

@Entity(name = "accounts_permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountPermission {
    @Id
    private String accountPermissionId;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "accountId")
    @ToString.Exclude
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    private AccountViewUsage viewUsage;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions;

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountPermission that = (AccountPermission) o;
        return Objects.equals(accountPermissionId, that.accountPermissionId) && Objects.equals(account, that.account)
                && viewUsage == that.viewUsage && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountPermissionId, account, viewUsage, permissions);
    }
}
