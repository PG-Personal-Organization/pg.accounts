package pg.accounts.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import pg.accounts.domain.AccountViewUsage;

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
}
