package pg.accounts.infrastructure.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pg.accounts.api.AccountModel;
import pg.accounts.application.AccountsService;
import pg.accounts.application.booking.BookingService;
import pg.accounts.domain.exception.*;
import pg.accounts.infrastructure.persistence.account.balances.AccountBalance;
import pg.accounts.infrastructure.persistence.account.balances.AccountBalancesRepository;
import pg.accounts.infrastructure.persistence.account.bookings.AccountBalanceBooking;
import pg.accounts.infrastructure.persistence.account.bookings.AccountBalanceBookingRepository;
import pg.context.auth.api.context.provider.ContextProvider;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final AccountBalanceBookingRepository accountBalanceBookingRepository;
    private final AccountBalancesRepository accountBalancesRepository;
    private final AccountsService accountsService;
    private final ContextProvider contextProvider;

    @Override
    @Transactional
    public String createBooking(final @NonNull String accountId, final @NonNull String currency, final @NonNull BigDecimal amount) {
        var userContext = contextProvider.tryToGetUserContext().orElseThrow();
        var accountModel = getAccountModel(accountId);
        if (!canAccountBalanceSustainNewBooking(currency, amount, accountModel)) {
            throw new InsufficientAccountBalanceException(accountId, currency, amount);
        }

        var accountBalance = accountBalancesRepository.findByAccount_accountIdAndCurrency(accountId, currency);
        var account = accountBalance.getAccount();

        var bookingId = "BK_" + account.getAccountNumber() + "_" + currency + "_" + UUID.randomUUID();
        var booking = AccountBalanceBooking.createNew(bookingId, accountBalance, amount, currency, userContext.getUserId());

        accountBalance.setBookedBalance(accountBalance.getBookedBalance().add(amount));
        accountBalancesRepository.save(accountBalance);

        accountBalanceBookingRepository.save(booking);
        accountsService.refreshAccount(accountId, account.getAccountNumber());

        return bookingId;
    }

    @Override
    @Transactional
    public void realizeAccountBooking(final @NonNull String accountId, final @NonNull String bookingId) {
        var userContext = contextProvider.tryToGetUserContext().orElseThrow();

        var booking = accountBalanceBookingRepository.findBooking(accountId, bookingId).orElseThrow(() -> new AccountBookingNotFoundException(bookingId, accountId));
        if (!booking.getBookedBy().equals(userContext.getUserId())) {
            throw new UserNotAuthorizedToProcessBookingBalanceException(userContext.getUserId(), bookingId);
        }

        var accountBalance = booking.getAccountBalance();
        accountBalance.setBookedBalance(accountBalance.getBookedBalance().subtract(booking.getAmount()));

        if (!canAccountBalanceBeReduced(accountBalance, booking)) {
            throw new InsufficientAccountBalanceException(accountId, booking.getCurrency(), booking.getAmount());
        }
        accountBalance.setBalance(accountBalance.getBalance().subtract(booking.getAmount()));
        accountBalancesRepository.save(accountBalance);

        booking.processBooking(userContext.getUserId());
        accountBalanceBookingRepository.save(booking);

        accountsService.refreshAccount(accountId);
    }

    @Override
    @Transactional
    public void releaseAccountBooking(final @NonNull String accountId, final @NonNull String bookingId) {
        var userContext = contextProvider.tryToGetUserContext().orElseThrow();

        var booking = accountBalanceBookingRepository.findBooking(accountId, bookingId).orElseThrow(() -> new AccountBookingNotFoundException(bookingId, accountId));
        if (!booking.getBookedBy().equals(userContext.getUserId())) {
            throw new UserNotAuthorizedToReleaseBookingBalanceException(userContext.getUserId(), bookingId);
        }

        var accountBalance = booking.getAccountBalance();
        accountBalance.setBookedBalance(accountBalance.getBookedBalance().subtract(booking.getAmount()));
        accountBalancesRepository.save(accountBalance);

        booking.releaseBooking(userContext.getUserId());
        accountBalanceBookingRepository.save(booking);

        accountsService.refreshAccount(accountId);
    }

    private AccountModel getAccountModel(final @NonNull String accountId) {
        return Optional.ofNullable(accountsService.getAccountById(accountId)).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private boolean canAccountBalanceSustainNewBooking(final String currency, final BigDecimal amount, final AccountModel accountModel) {
        return accountModel.getAvailableBalance(currency)
                .subtract(accountModel.getBookedBalance(currency))
                .compareTo(amount) >= 0;

    }

    private boolean canAccountBalanceBeReduced(final AccountBalance accountBalance, final AccountBalanceBooking booking) {
        return accountBalance.getBalance().compareTo(booking.getAmount()) >= 0;
    }
}
