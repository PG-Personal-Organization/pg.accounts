package pg.accounts.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountViewResponse implements Serializable {
    private Boolean found;
    private Boolean hasAccess;
    private AccountView account;
}
