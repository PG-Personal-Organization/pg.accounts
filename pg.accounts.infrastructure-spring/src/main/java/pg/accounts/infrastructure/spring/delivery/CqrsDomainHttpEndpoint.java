package pg.accounts.infrastructure.spring.delivery;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.accounts.api.AccountQuery;
import pg.accounts.api.AccountViewResponse;
import pg.accounts.api.booking.BookAccountBalanceCommand;
import pg.accounts.api.booking.ProcessAccountBalanceCommand;
import pg.accounts.api.booking.RelieveAccountBalanceCommand;
import pg.lib.cqrs.service.ServiceExecutor;

@RestController
@RequestMapping(CqrsUtils.CQRS_PATH)
@RequiredArgsConstructor
@Log4j2
@Tag(name = "CQRS")
public class CqrsDomainHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @PostMapping(CqrsUtils.ACCOUNT_QUERY_PATH)
    public AccountViewResponse getAccountView(final @Valid @NonNull @RequestBody AccountQuery query) {
        log.debug("Started execution of AccountQuery: {}", query);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping(CqrsUtils.BOOK_ACCOUNT_BALANCE_PATH)
    public String book(final @Valid @NonNull @RequestBody BookAccountBalanceCommand command) {
        log.debug("Started execution of BookAccountBalanceCommand: {}", command);
        return serviceExecutor.executeCommand(command);
    }

    @PostMapping(CqrsUtils.PROCESS_ACCOUNT_BALANCE_PATH)
    public Void process(final @Valid @NonNull @RequestBody ProcessAccountBalanceCommand command) {
        log.debug("Started execution of ProcessAccountBalanceCommand: {}", command);
        return serviceExecutor.executeCommand(command);
    }

    @PostMapping(CqrsUtils.RELIEVE_ACCOUNT_BALANCE_PATH)
    public Void relieve(final @Valid @NonNull @RequestBody RelieveAccountBalanceCommand command) {
        log.debug("Started execution of RelieveAccountBalanceCommand: {}", command);
        return serviceExecutor.executeCommand(command);
    }
}
