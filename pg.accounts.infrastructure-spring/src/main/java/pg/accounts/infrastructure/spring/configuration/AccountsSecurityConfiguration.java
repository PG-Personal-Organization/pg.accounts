package pg.accounts.infrastructure.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import pg.accounts.infrastructure.spring.delivery.CqrsUtils;
import pg.lib.common.spring.user.Roles;

@Configuration
public class AccountsSecurityConfiguration {

    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> accountsRequestCustomizer() {
        return requests -> requests
                .requestMatchers(CqrsUtils.CQRS_PATH + "/**")
                .hasRole(Roles.USER.name());
    }
}
