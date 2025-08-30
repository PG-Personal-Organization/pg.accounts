package pg.accounts.api.booking;

import lombok.*;
import pg.lib.cqrs.command.Command;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class BookAccountBalanceCommand implements Command<String> {
    private String accountId;
    private String currency;
    private BigDecimal amount;
}
