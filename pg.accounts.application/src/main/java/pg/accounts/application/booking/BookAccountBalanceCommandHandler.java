package pg.accounts.application.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pg.accounts.api.booking.BookAccountBalanceCommand;
import pg.accounts.application.AccountPermissionService;
import pg.accounts.domain.AccountViewUsage;
import pg.accounts.domain.exception.InsufficientUserPermissionsToAccountException;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.lib.cqrs.command.CommandHandler;

@RequiredArgsConstructor
public class BookAccountBalanceCommandHandler implements CommandHandler<BookAccountBalanceCommand, String> {
    private final ContextProvider contextProvider;
    private final BookingService bookingService;
    private final AccountPermissionService accountPermissionService;

    @Override
    @Transactional
    public String handle(final BookAccountBalanceCommand command) {
        var context = contextProvider.tryToGetUserContext().orElseThrow();
        if (!accountPermissionService.hasPermission(command.getAccountId(), AccountViewUsage.TRANSFER_ACCOUNT)) {
            throw new InsufficientUserPermissionsToAccountException(context.getUserId(), command.getAccountId(), AccountViewUsage.TRANSFER_ACCOUNT);
        }
        return bookingService.createBooking(command.getAccountId(), command.getCurrency(), command.getAmount());
    }
}
