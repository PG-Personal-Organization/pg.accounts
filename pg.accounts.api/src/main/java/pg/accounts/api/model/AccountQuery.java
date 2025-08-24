package pg.accounts.api.model;

import lombok.*;
import pg.accounts.domain.AccountViewUsage;
import pg.lib.cqrs.query.Query;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class AccountQuery implements Query<AccountViewResponse> {
    private String accountNumber;
    private AccountViewUsage viewUsage;
}
