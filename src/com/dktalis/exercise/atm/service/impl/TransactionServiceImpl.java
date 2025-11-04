package com.dktalis.exercise.atm.service.impl;

import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.exception.TransactionException;
import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.service.AbstractTransferService;
import com.dktalis.exercise.atm.service.AccountService;
import com.dktalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class TransactionServiceImpl implements TransactionService {

    private final AuthenticationProvider authenticationProvider;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserRepository userRepository;

    public TransactionServiceImpl
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
    public void deposit(BigDecimal depositAmount) throws SessionLoginException, TransactionException {
        if(!authenticationProvider.isAuthenticated()){
            throw new SessionLoginException("please login first");
        }

        if(depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException("invalid deposit amount");
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account fromAccount = accountRepository.findByUserId(currentUser.getId());

        //check debt
        Account debtAccount = fromAccount.getDebtAccount();
        if(debtAccount != null ){
            AbstractTransferService transferService = new DebtTransferServiceImpl(accountService, accountRepository, userRepository, authenticationProvider);
            transferService.transfer(debtAccount.getUser().getName(), depositAmount);
        }
        else {
            BigDecimal newBalance = fromAccount.getBalance().add(depositAmount);
            fromAccount.setBalance(newBalance);
            accountRepository.updateAccount(fromAccount);
            System.out.println("your balance is " + newBalance);
        }
    }

    @Override
    public void withdraw(BigDecimal amount) throws SessionLoginException, TransactionException {
        if(!authenticationProvider.isAuthenticated()){
            throw new SessionLoginException("please login first");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException("invalid withdraw amount");
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal newBalance = userAccount.getBalance().subtract(amount);

        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new TransactionException("insufficient balance.");
        }

        userAccount.setBalance(newBalance);
        accountRepository.updateAccount(userAccount);

        System.out.println("your balance is " + newBalance);
    }

    @Override
    public void getCurrentBalance() throws SessionLoginException {
        if(!authenticationProvider.isAuthenticated()){
            throw new SessionLoginException("please login first");
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal currentBalance = userAccount.getBalance();
        System.out.println("your balance is " + currentBalance);

        Account debtAccount = userAccount.getDebtAccount();
        if(debtAccount != null){
            System.out.println("Owed $" + debtAccount.getBalance() + " to " + debtAccount.getUser().getName());
        }

        Account receiveableAccount = userAccount.getReceivableAccount();
        if(receiveableAccount != null){
            System.out.println("Owed $" + receiveableAccount.getBalance() + " from " + receiveableAccount.getUser().getName());
        }

    }

    @Override
    public void transfer(String targetUser, BigDecimal transferAmount) throws SessionLoginException, TransactionException {
       if(!authenticationProvider.isAuthenticated()){
           throw new SessionLoginException("please login first");
       }

       User user =  userRepository.findByUserName(targetUser);
       if(user == null){
           throw new TransactionException("user not found");
       }

       Account targetAccount = accountRepository.findByUserId(user.getId());

       User currentUser = authenticationProvider.getAuthenticatedUser();
       Account sourceAccount = accountRepository.findByUserId(currentUser.getId());
       Account receiveableAccount = sourceAccount.getReceivableAccount();
       boolean hasReceiveableAccount = receiveableAccount != null && receiveableAccount.getAccountId().equals(targetAccount.getAccountId());

       AbstractTransferService transferService = null;
       if(hasReceiveableAccount){
           transferService = new ReceivableTransferServiceImpl(accountService, accountRepository, userRepository, authenticationProvider);
       }
       else {
           transferService = new NormalTransferServiceImpl(accountService, accountRepository, userRepository, authenticationProvider);
       }
       transferService.transfer(targetUser, transferAmount);
    }
}
