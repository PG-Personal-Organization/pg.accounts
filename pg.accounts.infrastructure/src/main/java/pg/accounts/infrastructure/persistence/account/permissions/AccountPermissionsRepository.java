package pg.accounts.infrastructure.persistence.account.permissions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPermissionsRepository extends JpaRepository<AccountPermission, String> {
}
