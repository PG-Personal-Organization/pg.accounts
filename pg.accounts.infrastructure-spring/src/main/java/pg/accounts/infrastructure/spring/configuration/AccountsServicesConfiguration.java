package pg.accounts.infrastructure.spring.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.accounts.application.AccountPermissionService;
import pg.accounts.application.AccountsService;
import pg.accounts.application.booking.BookingService;
import pg.accounts.infrastructure.persistence.account.balances.AccountBalancesRepository;
import pg.accounts.infrastructure.persistence.account.AccountRepository;
import pg.accounts.infrastructure.persistence.account.bookings.AccountBalanceBookingRepository;
import pg.accounts.infrastructure.service.AccountsServiceImpl;
import pg.accounts.infrastructure.service.BookingServiceImpl;
import pg.accounts.infrastructure.service.ContextAccountPermissionServiceImpl;
import pg.context.auth.api.context.provider.ContextProvider;

import java.util.Objects;

@Configuration
public class AccountsServicesConfiguration {

    @Bean
    public AccountsService accountService(final AccountRepository accountRepository, final CacheManager cacheManager) {
        var accountsCache = Objects.requireNonNull(cacheManager.getCache("accountsCache"));
        return new AccountsServiceImpl(accountRepository, accountsCache);
    }

    @Bean
    public BookingService bookingService(final AccountBalanceBookingRepository accountBalanceBookingRepository,
                                         final AccountBalancesRepository accountBalancesRepository,
                                         final AccountsService accountsService,
                                         final ContextProvider contextProvider) {
        return new BookingServiceImpl(accountBalanceBookingRepository, accountBalancesRepository, accountsService, contextProvider);
    }

    @Bean
    public AccountPermissionService accountPermissionService(final ContextProvider contextProvider, final AccountsService accountsService) {
        return new ContextAccountPermissionServiceImpl(contextProvider, accountsService);
    }
}
