package pg.accounts.api.booking;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pg.lib.cqrs.command.Command;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class ProcessAccountBalanceCommand implements Command<Void> {
    @NotBlank
    private String accountId;
    @NotBlank
    private String bookingId;
}
