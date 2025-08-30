package pg.accounts.infrastructure.spring.delivery;

public final class CqrsUtils {
    public static final String CQRS_PATH = "/api/cqrs/v1";

    public static final String BOOK_ACCOUNT_BALANCE_PATH = "/BookAccountBalanceCommand";
    public static final String PROCESS_ACCOUNT_BALANCE_PATH = "/ProcessAccountBalanceCommand";
    public static final String RELIEVE_ACCOUNT_BALANCE_PATH = "/RelieveAccountBalanceCommand";

    private CqrsUtils() { }
}
