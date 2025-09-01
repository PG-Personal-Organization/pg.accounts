package pg.accounts.api.booking;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pg.lib.cqrs.command.Command;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProcessAccountBalanceCommand implements Command<Void>, Serializable {
    @NotBlank
    private String accountId;
    @NotBlank
    private String bookingId;
}
