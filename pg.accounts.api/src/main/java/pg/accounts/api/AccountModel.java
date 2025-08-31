package pg.accounts.api;

import lombok.*;
import pg.accounts.domain.AccountViewUsage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel implements AccountView {
    @NonNull
    private String accountId;
    @NonNull
    private String accountNumber;
    @NonNull
    private Map</* currency */String, BigDecimal> availableBalance;
    @NonNull
    private Map</* currency */String, BigDecimal> bookedBalance;
    @NonNull
    private Map<AccountViewUsage, Set</* permission */String>> requiredPermissions;

    @Override
    public BigDecimal getAvailableBalance(final @NonNull String currency) {
        return availableBalance.getOrDefault(currency, BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getBookedBalance(final @NonNull String currency) {
        return bookedBalance.getOrDefault(currency, BigDecimal.ZERO);
    }
}
