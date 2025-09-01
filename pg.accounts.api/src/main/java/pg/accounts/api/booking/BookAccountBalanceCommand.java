package pg.accounts.api.booking;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pg.lib.cqrs.command.Command;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BookAccountBalanceCommand implements Command<String>, Serializable {
    @NotBlank
    private String accountId;
    @NotBlank
    private String currency;
    @NonNull
    private BigDecimal amount;
}
