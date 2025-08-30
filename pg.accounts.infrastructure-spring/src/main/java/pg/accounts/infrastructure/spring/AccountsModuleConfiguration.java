package pg.accounts.infrastructure.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pg.accounts.infrastructure.spring.configuration.*;
import pg.context.auth.api.security.ContextBasedSecurityConfiguration;
import pg.lib.common.spring.config.CommonModuleConfiguration;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;
import pg.lib.remote.cqrs.config.RemoteModulesCqrsConfiguration;

@Configuration
@Import({
        AccountsPersistenceConfiguration.class,
        AccountsSecurityConfiguration.class,
        AccountsCqrsConfiguration.class,
        AccountsCacheConfiguration.class,
        AccountsServicesConfiguration.class,

        CommonModuleConfiguration.class,
        CommandQueryAutoConfiguration.class,
        ContextBasedSecurityConfiguration.class,
        RemoteModulesCqrsConfiguration.class,
})
@ComponentScan({"pg.accounts.infrastructure.service"})
public class AccountsModuleConfiguration {
}
