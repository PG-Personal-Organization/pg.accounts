package pg.accounts.api.booking;

import lombok.*;
import pg.lib.cqrs.command.Command;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class RelieveAccountBalanceCommand implements Command<Void> {
    private String accountId;
    private String bookingId;
}
