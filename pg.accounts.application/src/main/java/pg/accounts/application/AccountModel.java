package pg.accounts.application;

import lombok.*;
import pg.accounts.api.model.AccountView;
import pg.accounts.domain.AccountViewUsage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel implements AccountView {
    private String accountId;
    private String accountNumber;
    private Map</* currency */String, BigDecimal> availableBalance;
    private Map</* currency */String, BigDecimal> bookedBalance;
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
