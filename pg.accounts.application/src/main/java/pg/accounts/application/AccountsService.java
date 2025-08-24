package pg.accounts.application;

import lombok.NonNull;

public interface AccountsService {
    AccountModel getAccountById(@NonNull String accountId);

    AccountModel getAccountByNumber(@NonNull String accountNumber);
}
