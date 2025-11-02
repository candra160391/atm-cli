package com.dktalis.exercise.atm.service.impl;

import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.service.IntrabankService;
import java.math.BigDecimal;

public class IntrabankServiceImpl implements IntrabankService {

    AuthenticationProvider authenticationProvider;
    AccountRepository accountRepository;

    public IntrabankServiceImpl(AuthenticationProvider authenticationProvider, AccountRepository accountRepository){
        this.authenticationProvider = authenticationProvider;
        this.accountRepository = accountRepository;
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
    }

    @Override
    public void transfer() {
    }
}
