package pg.accounts.api.booking;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pg.lib.cqrs.command.Command;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class BookAccountBalanceCommand implements Command<String> {
    @NotBlank
    private String accountId;
    @NotBlank
    private String currency;
    @NonNull
    private BigDecimal amount;
}
