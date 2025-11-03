package com.dktalis.exercise.atm.service.impl;

import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.service.AccountService;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    public AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(User user){
       return accountRepository.addAccount(user);
    }

    public void upsertDebtAccount(Account sourceAccount, Account targetAccount, BigDecimal debtAmount) {
        Account owedToAccount = sourceAccount.getToDebtAccount().get(targetAccount.getAccountId());

        if(owedToAccount == null){
            owedToAccount = new Account();
            owedToAccount.setUser(targetAccount.getUser());
            owedToAccount.setBalance(debtAmount);
        }
        else {
            BigDecimal newBalance = owedToAccount.getBalance().add(debtAmount);
            owedToAccount.setBalance(newBalance);
        }

        Account owedFromAccount = targetAccount.getFromDebtAccount().get(sourceAccount.getAccountId());
        if(owedFromAccount == null){
            owedFromAccount = new Account();
            owedFromAccount.setUser(sourceAccount.getUser());
            owedFromAccount.setBalance(debtAmount);
        }
        else {
            BigDecimal newBalance = owedFromAccount.getBalance().add(debtAmount);
            owedFromAccount.setBalance(newBalance);
        }

        sourceAccount.getToDebtAccount().put(targetAccount.getAccountId(), owedToAccount);
        targetAccount.getFromDebtAccount().put(sourceAccount.getAccountId(), owedFromAccount);

        accountRepository.upsertDebtAccount(sourceAccount, targetAccount);
    }

    public void updateRemainBalance(Account sourceAccount, boolean isDebtAmount, BigDecimal remainingBalance) {
        BigDecimal newBalance = isDebtAmount ? BigDecimal.ZERO : remainingBalance;
        accountRepository.updateBalance(sourceAccount.getAccountId(), newBalance);
        System.out.println("Your balance is $" + newBalance);
    }

    public void updateTargetBalance(String targetUser, Account targetAccount, BigDecimal transferAmount) {
        BigDecimal newBalance = targetAccount.getBalance().add(transferAmount);
        accountRepository.updateBalance(targetAccount.getAccountId(), newBalance);
        System.out.println("Transferred $" + transferAmount + " to " + targetUser);
    }

}
