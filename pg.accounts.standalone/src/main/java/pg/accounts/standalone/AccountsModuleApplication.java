package pg.accounts.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pg.accounts.infrastructure.spring.AccountsModuleConfiguration;

@SpringBootApplication
@Import({
        AccountsModuleConfiguration.class
})
public class AccountsModuleApplication {
    public static void main(final String[] args) {
        SpringApplication.run(AccountsModuleApplication.class, args);
    }
}
