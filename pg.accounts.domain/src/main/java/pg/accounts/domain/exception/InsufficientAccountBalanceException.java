package pg.accounts.domain.exception;

import java.math.BigDecimal;

public class InsufficientAccountBalanceException extends RuntimeException {
    public InsufficientAccountBalanceException(final String accountId, final String currency, final BigDecimal amount) {
        super(String.format("Insufficient account balance for account with id %s for currency %s and amount %s", accountId, currency, amount));
    }
}
