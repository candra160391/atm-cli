package com.dkatalis.exercise.atm.service.impl;

import com.dkatalis.exercise.atm.Enum.TransactionTypeEnum;
import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.repository.AccountRepository;
import com.dkatalis.exercise.atm.service.AccountService;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(User user){
       return accountRepository.createNewAccount(user);
    }

    @Override
    public Account updateAccount(Account account) {
       return accountRepository.updateAccount(account);
    }

    public Account upsertDebtAccount(Account sourceAccount, Account targetAccount, BigDecimal debtAmount, TransactionTypeEnum transactionType) {

        Account debtAccount = sourceAccount.getDebtAccount();

        if(debtAccount == null){
            debtAccount = new Account();
            debtAccount.setAccountId(targetAccount.getAccountId());
            debtAccount.setUser(targetAccount.getUser());
            debtAccount.setBalance(debtAmount);
        }
        else {
            BigDecimal newBalance = BigDecimal.ZERO;

            if(transactionType.equals(TransactionTypeEnum.CREDIT)){
                newBalance = debtAccount.getBalance().add(debtAmount);
            }
            else if(transactionType.equals(TransactionTypeEnum.DEBET)){
                newBalance = debtAccount.getBalance().subtract(debtAmount);
            }

            debtAccount.setBalance(newBalance);
        }

        if(debtAccount.getBalance().compareTo(BigDecimal.ZERO) > 0){
            sourceAccount.setDebtAccount(debtAccount);
        }
        else {
            sourceAccount.setDebtAccount(null);
        }

        return accountRepository.updateAccount(sourceAccount);
    }

    public Account upsertReceivableAccount(Account sourceAccount, Account targetAccount, BigDecimal debtAmount, TransactionTypeEnum transactionType) {

        Account receiveableAccount = sourceAccount.getReceivableAccount();
        if(receiveableAccount == null){
            receiveableAccount = new Account();
            receiveableAccount.setAccountId(targetAccount.getAccountId());
            receiveableAccount.setUser(targetAccount.getUser());
            receiveableAccount.setBalance(debtAmount);
        }
        else {
            BigDecimal newBalance = BigDecimal.ZERO;

            if(transactionType.equals(TransactionTypeEnum.CREDIT)){
                newBalance = receiveableAccount.getBalance().add(debtAmount);
            }
            else if(transactionType.equals(TransactionTypeEnum.DEBET)){
                newBalance = receiveableAccount.getBalance().subtract(debtAmount);
            }

            receiveableAccount.setBalance(newBalance);
        }

        if(receiveableAccount.getBalance().compareTo(BigDecimal.ZERO) > 0){
            sourceAccount.setReceivableAccount(receiveableAccount);
        }
        else {
            sourceAccount.setReceivableAccount(null);
        }

        return accountRepository.updateAccount(sourceAccount);
    }
}
