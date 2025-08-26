package pg.accounts.infrastructure.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.accounts.application.AccountQueryHandler;
import pg.accounts.application.AccountsService;
import pg.context.auth.api.context.provider.ContextProvider;

@Configuration
public class AccountsCqrsConfiguration {

    @Bean
    public AccountQueryHandler accountQueryHandler(final AccountsService accountsService, final ContextProvider contextProvider) {
        return new AccountQueryHandler(accountsService, contextProvider);
    }
}
