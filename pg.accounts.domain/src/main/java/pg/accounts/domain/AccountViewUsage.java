package pg.accounts.domain;

public enum AccountViewUsage {
    CREDITED_ACCOUNT, // view only as a target of payments
    TRANSFER_ACCOUNT, // full access to account as an owner
}
