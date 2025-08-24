package pg.accounts.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPermissionsRepository extends JpaRepository<AccountPermission, String> {
}
