package pg.accounts.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class AccountsCacheConfiguration {
    private static final String ACCOUNTS_CACHE = "accountsCache";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(ACCOUNTS_CACHE);
    }

    /**
     * Users cache cache.
     *
     * @return the cache
     */
    @Bean
    @Qualifier(ACCOUNTS_CACHE)
    public Cache accountsCache() {
        return cacheManager().getCache(ACCOUNTS_CACHE);
    }
}
