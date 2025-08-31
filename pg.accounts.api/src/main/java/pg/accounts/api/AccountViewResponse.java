package pg.accounts.api;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountViewResponse implements Serializable {
    @NonNull
    private Boolean found;
    @NonNull
    private Boolean hasAccess;
    @NonNull
    private AccountModel account;
}
