package pg.accounts.api.model;

import lombok.NonNull;

import java.math.BigDecimal;

public interface AccountView {
    String getAccountId();

    String getAccountNumber();

    BigDecimal getAvailableBalance(@NonNull String currency);

    BigDecimal getBookedBalance(@NonNull String currency);
}
