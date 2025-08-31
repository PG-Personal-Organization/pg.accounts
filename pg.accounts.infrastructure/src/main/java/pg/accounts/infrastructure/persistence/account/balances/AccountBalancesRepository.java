package pg.accounts.infrastructure.persistence.account.balances;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalancesRepository extends JpaRepository<AccountBalance, String> {
    @SuppressWarnings("checkstyle:MethodName")
    AccountBalance findByAccount_accountIdAndCurrency(String accountId, String currency);
}
