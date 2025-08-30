package pg.accounts.infrastructure.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import pg.accounts.api.AccountModel;
import pg.accounts.application.AccountsService;
import pg.accounts.infrastructure.persistence.AccountBalance;
import pg.accounts.infrastructure.persistence.AccountEntity;
import pg.accounts.infrastructure.persistence.AccountPermission;
import pg.accounts.infrastructure.persistence.AccountRepository;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountsService {
    private final AccountRepository accountRepository;
    private final Cache accountsCache;

    @Override
    public AccountModel getAccountById(final @NonNull String accountId) {
        if (accountsCache == null) {
            return toAccountModel(accountRepository.findById(accountId).orElseThrow());
        }
        var accountModel = accountsCache.get(accountId, AccountModel.class);
        if (accountModel == null) {
            accountModel = toAccountModel(accountRepository.findById(accountId).orElseThrow());
            accountsCache.put(accountId, accountModel);
        }
        return accountModel;
    }

    @Override
    public AccountModel getAccountByNumber(final @NonNull String accountNumber) {
        if (accountsCache == null) {
            return toAccountModel(accountRepository.findByAccountNumber(accountNumber).orElseThrow());
        }
        var accountModel = accountsCache.get(accountNumber, AccountModel.class);
        if (accountModel == null) {
            accountModel = toAccountModel(accountRepository.findByAccountNumber(accountNumber).orElseThrow());
            accountsCache.put(accountNumber, accountModel);
        }
        return accountModel;
    }

    private AccountModel toAccountModel(final @NonNull AccountEntity accountEntity) {
        var permissions = accountEntity.getAccountPermissions().stream().collect(Collectors.groupingBy(
                AccountPermission::getViewUsage,
                flatMapping(ap -> ap.getPermissions().stream(), toSet())
        ));

        var balances = accountEntity.getBalances();

        var bookedBalance = balances.stream()
                .filter(b -> b.getBookedBalance() != null)
                .collect(Collectors.groupingBy(
                        AccountBalance::getCurrency,
                        reducing(BigDecimal.ZERO, AccountBalance::getBookedBalance, BigDecimal::add)
                ));

        var availableBalance = balances.stream()
                .filter(b -> b.getBalance() != null)
                .collect(Collectors.groupingBy(
                        AccountBalance::getCurrency,
                        reducing(BigDecimal.ZERO, AccountBalance::getBalance, BigDecimal::add)
                ));

        return AccountModel.builder()
                .accountId(accountEntity.getAccountId())
                .accountNumber(accountEntity.getAccountNumber())
                .requiredPermissions(permissions)
                .availableBalance(availableBalance)
                .bookedBalance(bookedBalance)
                .build();
    }
}
