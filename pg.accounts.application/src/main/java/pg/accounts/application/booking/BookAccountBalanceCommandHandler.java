package pg.accounts.application.booking;

import lombok.RequiredArgsConstructor;
import pg.accounts.api.booking.BookAccountBalanceCommand;
import pg.lib.cqrs.command.CommandHandler;

@RequiredArgsConstructor
public class BookAccountBalanceCommandHandler implements CommandHandler<BookAccountBalanceCommand, String> {

    @Override
    public String handle(final BookAccountBalanceCommand command) {
        return "";
    }
}
