package pg.accounts.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalancesRepository extends JpaRepository<AccountBalance, String> {
}
