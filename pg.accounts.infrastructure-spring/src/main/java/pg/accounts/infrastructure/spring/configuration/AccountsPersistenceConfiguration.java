package pg.accounts.infrastructure.spring.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("pg.accounts.infrastructure.persistence")
@EnableJpaRepositories("pg.accounts.infrastructure.persistence")
public class AccountsPersistenceConfiguration {
}
