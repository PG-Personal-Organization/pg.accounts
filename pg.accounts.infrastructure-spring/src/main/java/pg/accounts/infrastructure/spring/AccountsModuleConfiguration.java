package pg.accounts.infrastructure.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pg.accounts.infrastructure.spring.configuration.AccountsCqrsConfiguration;
import pg.accounts.infrastructure.spring.configuration.AccountsPersistenceConfiguration;
import pg.accounts.infrastructure.spring.configuration.AccountsSecurityConfiguration;
import pg.context.auth.api.security.ContextBasedSecurityConfiguration;
import pg.lib.common.spring.config.CommonModuleConfiguration;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;
import pg.lib.remote.cqrs.config.RemoteModulesCqrsConfiguration;

@Configuration
@Import({
        AccountsPersistenceConfiguration.class,
        AccountsSecurityConfiguration.class,
        AccountsCqrsConfiguration.class,

        CommonModuleConfiguration.class,
        CommandQueryAutoConfiguration.class,
        ContextBasedSecurityConfiguration.class,
        RemoteModulesCqrsConfiguration.class,
})
@ComponentScan({"pg.accounts.infrastructure.service", "pg.accounts.application.provider", "pg.accounts.application.service"})
public class AccountsModuleConfiguration {
}
