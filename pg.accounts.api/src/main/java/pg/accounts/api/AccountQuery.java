package pg.accounts.api;

import lombok.*;
import pg.accounts.domain.AccountViewUsage;
import pg.lib.cqrs.query.Query;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AccountQuery implements Query<AccountViewResponse>, Serializable {
    private String accountNumber;
    private AccountViewUsage viewUsage;
}
