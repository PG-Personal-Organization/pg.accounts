package pg.accounts.infrastructure.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.accounts.application.AccountPermissionService;
import pg.accounts.application.AccountQueryHandler;
import pg.accounts.application.AccountsService;
import pg.accounts.application.booking.BookAccountBalanceCommandHandler;
import pg.accounts.application.booking.BookingService;
import pg.accounts.application.booking.ProcessAccountBalanceCommandHandler;
import pg.accounts.application.booking.RelieveAccountBalanceCommandHandler;
import pg.accounts.infrastructure.spring.delivery.CqrsDomainHttpEndpoint;
import pg.context.auth.api.context.provider.ContextProvider;
import pg.lib.cqrs.service.ServiceExecutor;

@Configuration
public class AccountsCqrsConfiguration {

    @Bean
    public CqrsDomainHttpEndpoint cqrsDomainHttpEndpoint(final ServiceExecutor serviceExecutor) {
        return new CqrsDomainHttpEndpoint(serviceExecutor);
    }

    @Bean
    public AccountQueryHandler accountQueryHandler(final AccountsService accountsService,
                                                   final AccountPermissionService accountPermissionService) {
        return new AccountQueryHandler(accountsService, accountPermissionService);
    }

    @Bean
    public BookAccountBalanceCommandHandler bookAccountBalanceCommandHandler(final ContextProvider contextProvider,
                                                                             final BookingService bookingService,
                                                                             final AccountPermissionService accountPermissionService) {
        return new BookAccountBalanceCommandHandler(contextProvider, bookingService, accountPermissionService);
    }

    @Bean
    public ProcessAccountBalanceCommandHandler processAccountBalanceCommandHandler(final ContextProvider contextProvider,
                                                                                   final BookingService bookingService,
                                                                                   final AccountPermissionService accountPermissionService) {
        return new ProcessAccountBalanceCommandHandler(contextProvider, bookingService, accountPermissionService);
    }

    @Bean
    public RelieveAccountBalanceCommandHandler relieveAccountBalanceCommandHandler(final ContextProvider contextProvider,
                                                                                   final BookingService bookingService,
                                                                                   final AccountPermissionService accountPermissionService) {
        return new RelieveAccountBalanceCommandHandler(contextProvider, bookingService, accountPermissionService);
    }
}
