package pg.accounts.infrastructure.spring.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pg.accounts.application.AccountsService;
import pg.accounts.infrastructure.persistence.AccountRepository;
import pg.accounts.infrastructure.service.AccountServiceImpl;

import java.util.Objects;

@Configuration
public class AccountsServicesConfiguration {

    @Bean
    public AccountsService accountService(final AccountRepository accountRepository, final CacheManager cacheManager) {
        var accountsCache = Objects.requireNonNull(cacheManager.getCache("accountsCache"));
        return new AccountServiceImpl(accountRepository, accountsCache);
    }
}
