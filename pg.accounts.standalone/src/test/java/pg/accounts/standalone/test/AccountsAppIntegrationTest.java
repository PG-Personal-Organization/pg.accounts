package pg.accounts.standalone.test;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pg.accounts.standalone.config.AccountsIntegrationTest;

@AccountsIntegrationTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AccountsAppIntegrationTest {
    private final Environment environment;

    @Test
    void contextLoads() {
        Assertions.assertThat(environment).isNotNull();
    }

}
