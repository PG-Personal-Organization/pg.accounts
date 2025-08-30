package pg.accounts.api;

import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

public interface AccountView extends Serializable {
    String getAccountId();

    String getAccountNumber();

    BigDecimal getAvailableBalance(@NonNull String currency);

    BigDecimal getBookedBalance(@NonNull String currency);
}
