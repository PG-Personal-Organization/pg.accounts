package pg.accounts.application.booking;

import lombok.RequiredArgsConstructor;
import pg.accounts.api.booking.RelieveAccountBalanceCommand;
import pg.lib.cqrs.command.CommandHandler;

@RequiredArgsConstructor
public class RelieveAccountBalanceCommandHandler implements CommandHandler<RelieveAccountBalanceCommand, Void> {

    @Override
    public Void handle(final RelieveAccountBalanceCommand command) {
        return null;
    }
}
