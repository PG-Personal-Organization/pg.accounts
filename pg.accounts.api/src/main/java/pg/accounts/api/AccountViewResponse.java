package pg.accounts.api;

import lombok.*;
import org.springframework.lang.Nullable;

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
    @Nullable
    private AccountModel account;
}
