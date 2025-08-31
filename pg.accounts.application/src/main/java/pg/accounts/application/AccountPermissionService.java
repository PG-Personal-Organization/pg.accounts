package pg.accounts.application;

import lombok.NonNull;
import pg.accounts.api.AccountModel;
import pg.accounts.domain.AccountViewUsage;

public interface AccountPermissionService {
    boolean hasPermission(@NonNull String accountId, @NonNull AccountViewUsage permission);

    boolean hasPermissionsToAccount(@NonNull AccountModel account, @NonNull AccountViewUsage permission);
}
