package pg.accounts.standalone.test;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import pg.accounts.api.booking.BookAccountBalanceCommand;
import pg.accounts.api.booking.ProcessAccountBalanceCommand;
import pg.accounts.api.booking.RelieveAccountBalanceCommand;
import pg.accounts.infrastructure.persistence.account.AccountEntity;
import pg.accounts.infrastructure.persistence.account.AccountRepository;
import pg.accounts.infrastructure.persistence.account.balances.AccountBalance;
import pg.accounts.infrastructure.persistence.account.balances.AccountBalancesRepository;
import pg.accounts.infrastructure.persistence.account.permissions.AccountPermission;
import pg.accounts.infrastructure.persistence.account.permissions.AccountPermissionsRepository;
import pg.accounts.infrastructure.persistence.account.bookings.AccountBalanceBookingRepository;
import pg.accounts.domain.AccountViewUsage;
import pg.accounts.standalone.config.AccountsIntegrationTest;
import pg.context.auth.domain.context.UserContext;
import pg.lib.common.spring.storage.HeadersHolder;
import pg.lib.cqrs.service.ServiceExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pg.lib.common.spring.auth.HeaderNames.CONTEXT_TOKEN;

@AccountsIntegrationTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class AccountsAppIntegrationTest {

    private final ServiceExecutor serviceExecutor;
    private final HeadersHolder headersHolder;
    private final CacheManager cacheManager;

    private final AccountRepository accountRepository;
    private final AccountPermissionsRepository accountPermissionsRepository;
    private final AccountBalancesRepository accountBalancesRepository;
    private final AccountBalanceBookingRepository accountBalanceBookingRepository;

    private String accountId;
    private final String currency = "PLN";
    private final UserContext userContext = UserContext.builder()
            .userId("1")
            .username("MOCK_USER")
            .contextToken("MOCK_TOKEN")
            .roles(Set.of("VIEW", "BOOK"))
            .build();

    @BeforeEach
    void setUp() {
        accountBalanceBookingRepository.deleteAll();
        accountBalancesRepository.deleteAll();
        accountPermissionsRepository.deleteAll();
        accountRepository.deleteAll();

        accountId = UUID.randomUUID().toString();
        var account = AccountEntity.builder()
                .accountId(accountId)
                .accountNumber("1234567890")
                .name("Test Account")
                .multiCurrency(false)
                .currency(currency)
                .blocked(false)
                .build();
        accountRepository.save(account);

        var balance = AccountBalance.builder()
                .balanceId(UUID.randomUUID().toString())
                .account(account)
                .currency(currency)
                .balance(BigDecimal.valueOf(1000))
                .bookedBalance(BigDecimal.ZERO)
                .build();
        accountBalancesRepository.save(balance);

        var permissions = List.of(
                AccountPermission.builder()
                .accountPermissionId(UUID.randomUUID().toString())
                .account(account)
                .viewUsage(AccountViewUsage.CREDITED_ACCOUNT)
                .permissions(Set.of("VIEW"))
                .build(),
                AccountPermission.builder()
                .accountPermissionId(UUID.randomUUID().toString())
                .account(account)
                .viewUsage(AccountViewUsage.TRANSFER_ACCOUNT)
                .permissions(Set.of("BOOK"))
                .build()
        );
        accountPermissionsRepository.saveAll(permissions);
        account.setAccountPermissions(permissions);
        account.setBalances(List.of(balance));
        accountRepository.save(account);

        headersHolder.putHeader(CONTEXT_TOKEN, userContext.getContextToken());
        Objects.requireNonNull(cacheManager.getCache("contextCache")).put(userContext.getContextToken(), userContext);
    }

    @ParameterizedTest
    @MethodSource("shouldProperlyOperateAccounts")
    void shouldProperlyOperateAccountsTest(BigDecimal bookingAmount) {
        // 1. Book
        String bookingId = serviceExecutor.executeCommand(BookAccountBalanceCommand.of(accountId, currency, bookingAmount));
        assertThat(bookingId).isNotBlank();

        var booking = accountBalanceBookingRepository.findById(bookingId).orElseThrow();
        assertThat(booking.getAmount()).isEqualByComparingTo(bookingAmount);
        assertThat(booking.getAccountBalance().getBookedBalance()).isEqualByComparingTo(bookingAmount);

        // 2. Process
        serviceExecutor.executeCommand(ProcessAccountBalanceCommand.of(accountId, bookingId));
        var processed = accountBalanceBookingRepository.findById(bookingId).orElseThrow();
        assertThat(processed.getProcessedDate()).isNotNull();
        assertThat(processed.getAccountBalance().getBalance()).isEqualByComparingTo(BigDecimal.valueOf(1000).subtract(bookingAmount));

        // 3. Release (this would fail if already processed, but good to test both paths separately)
        // Reset booking again for release scenario
        String releaseBookingId = serviceExecutor.executeCommand(BookAccountBalanceCommand.of(accountId, currency, bookingAmount));
        serviceExecutor.executeCommand(RelieveAccountBalanceCommand.of(accountId, releaseBookingId));
        var released = accountBalanceBookingRepository.findById(releaseBookingId).orElseThrow();
        assertThat(released.getClosedDate()).isNotNull();
        assertThat(released.getAccountBalance().getBookedBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    static Stream<Arguments> shouldProperlyOperateAccounts() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(100)),
                Arguments.of(BigDecimal.valueOf(250))
        );
    }
}
