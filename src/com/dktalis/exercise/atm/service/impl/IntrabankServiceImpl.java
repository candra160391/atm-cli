package com.dktalis.exercise.atm.service.impl;

import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.service.AccountService;
import com.dktalis.exercise.atm.service.IntrabankService;
import java.math.BigDecimal;

public class IntrabankServiceImpl implements IntrabankService {

    AuthenticationProvider authenticationProvider;
    AccountRepository accountRepository;
    AccountService accountService;
    UserRepository userRepository;

    public IntrabankServiceImpl
    (
        AuthenticationProvider authenticationProvider,
        AccountRepository accountRepository,
        UserRepository userRepository,
        AccountService accountService
    )
    {
        this.authenticationProvider = authenticationProvider;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Override
    public void deposit(BigDecimal amount) throws Exception {
        if(!authenticationProvider.isAuthenticated()){
            throw new Exception("please login first");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("invalid deposit amount");
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal newBalance = userAccount.getBalance().add(amount);

        accountRepository.updateBalance(userAccount.getAccountId(), newBalance);

        System.out.println("your balance is " + newBalance);
    }

    @Override
    public void withdraw(BigDecimal amount) throws Exception {
        if(!authenticationProvider.isAuthenticated()){
            throw new Exception("please login first");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("invalid withdraw amount");
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal newBalance = userAccount.getBalance().subtract(amount);

        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new Exception("insufficient balance.");
        }

        accountRepository.updateBalance(userAccount.getAccountId(), newBalance);

        System.out.println("your balance is " + newBalance);
    }

    @Override
    public void getCurrentBalance() throws Exception {
        if(!authenticationProvider.isAuthenticated()){
            throw new Exception("please login first");
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal currentBalance = userAccount.getBalance();
        System.out.println("your balance is " + currentBalance);

        userAccount.getToDebtAccount()
                .values()
                .forEach(debtAccount -> {
                    System.out.println("Owe $" + debtAccount.getBalance() + " to " + debtAccount.getUser().getName());
                });

        userAccount.getFromDebtAccount()
                .values()
                .forEach(debtAccount -> {
                    System.out.println("Owe $" + debtAccount.getBalance() + " from " + debtAccount.getUser().getName());
                });

    }

    @Override
    public void transfer(String targetUser, BigDecimal transferAmount) throws Exception {
       User user =  userRepository.findByUserName(targetUser);
       if(user == null){
           throw new Exception("user not found");
       }

       Account targetAccount = accountRepository.findByUserId(user.getId());

       User currentUser = authenticationProvider.getAuthenticatedUser();
       Account sourceAccount = accountRepository.findByUserId(currentUser.getId());
       BigDecimal currentBalance = sourceAccount.getBalance();

       if(currentBalance.compareTo(BigDecimal.ZERO) <= 0){
           throw new Exception("Insufficient balance");
       }

       BigDecimal remainingBalance = currentBalance.subtract(transferAmount);

       boolean isDebtAmount = remainingBalance.compareTo(BigDecimal.ZERO) < 0;

       if(isDebtAmount){
           transferAmount = currentBalance;
       }

        accountService.updateTargetBalance(targetUser, targetAccount, transferAmount);
        accountService.updateRemainBalance(sourceAccount, isDebtAmount, remainingBalance);

        if(isDebtAmount){
            BigDecimal debtAmount = remainingBalance.negate();
            accountService.upsertDebtAccount(sourceAccount, targetAccount, debtAmount);
            System.out.println("Owe " + debtAmount+ " to " + targetUser);
        }
    }
}
