package pg.accounts.application.booking;

import lombok.RequiredArgsConstructor;
import pg.accounts.api.booking.ProcessAccountBalanceCommand;
import pg.lib.cqrs.command.CommandHandler;

@RequiredArgsConstructor
public class ProcessAccountBalanceCommandHandler implements CommandHandler<ProcessAccountBalanceCommand, Void> {

    @Override
    public Void handle(final ProcessAccountBalanceCommand command) {
        return null;
    }
}
