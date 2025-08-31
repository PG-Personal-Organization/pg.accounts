package pg.accounts.application;

import lombok.NonNull;
import pg.accounts.api.AccountModel;

public interface AccountsService {
    AccountModel getAccountById(@NonNull String accountId);

    AccountModel getAccountByNumber(@NonNull String accountNumber);

    void refreshAccount(@NonNull String accountId, @NonNull String accountNumber);

    void refreshAccount(@NonNull String accountId);
}
