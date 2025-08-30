package pg.accounts.infrastructure.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.accounts.application.AccountQueryHandler;
import pg.accounts.application.AccountsService;
import pg.accounts.application.booking.BookAccountBalanceCommandHandler;
import pg.accounts.application.booking.ProcessAccountBalanceCommandHandler;
import pg.accounts.application.booking.RelieveAccountBalanceCommandHandler;
import pg.context.auth.api.context.provider.ContextProvider;

@Configuration
public class AccountsCqrsConfiguration {

    @Bean
    public AccountQueryHandler accountQueryHandler(final AccountsService accountsService, final ContextProvider contextProvider) {
        return new AccountQueryHandler(accountsService, contextProvider);
    }

    @Bean
    public BookAccountBalanceCommandHandler bookAccountBalanceCommandHandler() {
        return new BookAccountBalanceCommandHandler();
    }

    @Bean
    public ProcessAccountBalanceCommandHandler processAccountBalanceCommandHandler() {
        return new ProcessAccountBalanceCommandHandler();
    }

    @Bean
    public RelieveAccountBalanceCommandHandler relieveAccountBalanceCommandHandler() {
        return new RelieveAccountBalanceCommandHandler();
    }
}
