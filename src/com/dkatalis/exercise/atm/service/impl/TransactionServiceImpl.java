package com.dkatalis.exercise.atm.service.impl;

import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.exception.TransactionException;
import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.repository.AccountRepository;
import com.dkatalis.exercise.atm.repository.UserRepository;
import com.dkatalis.exercise.atm.service.AbstractTransferService;
import com.dkatalis.exercise.atm.service.AccountService;
import com.dkatalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class TransactionServiceImpl implements TransactionService {

    private final AuthenticationProvider authenticationProvider;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final MessageProvider messageProvider;

    public TransactionServiceImpl
    (
        AuthenticationProvider authenticationProvider,
        MessageProvider messageProvider,
        AccountRepository accountRepository,
        UserRepository userRepository,
        AccountService accountService
    )
    {
        this.authenticationProvider = authenticationProvider;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.messageProvider = messageProvider;
    }

    @Override
    public void deposit(BigDecimal depositAmount) throws SessionLoginException, TransactionException {
        if(!authenticationProvider.isAuthenticated()){
            throw new SessionLoginException(messageProvider.get("error.force.access"));
        }

        if(depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException(messageProvider.get("error.invalid.deposit.amount"));
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account fromAccount = accountRepository.findByUserId(currentUser.getId());

        //check debt
        Account debtAccount = fromAccount.getDebtAccount();
        if(debtAccount != null ){
            AbstractTransferService transferService = new DebtTransferServiceImpl(accountService, accountRepository, userRepository, authenticationProvider, messageProvider);
            transferService.transfer(debtAccount.getUser().getName(), depositAmount);
        }
        else {
            BigDecimal newBalance = fromAccount.getBalance().add(depositAmount);
            fromAccount.setBalance(newBalance);
            accountRepository.updateAccount(fromAccount);
            System.out.println(messageProvider.get("display.balance", fromAccount.getBalance()));
        }
    }

    @Override
    public void withdraw(BigDecimal amount) throws SessionLoginException, TransactionException {
        if(!authenticationProvider.isAuthenticated()){
            throw new SessionLoginException(messageProvider.get("error.force.access"));
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException(messageProvider.get("error.invalid.withdraw.amount"));
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal newBalance = userAccount.getBalance().subtract(amount);

        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new TransactionException(messageProvider.get("error.not.enough.balance"));
        }

        userAccount.setBalance(newBalance);
        accountRepository.updateAccount(userAccount);

        System.out.println(messageProvider.get("display.balance", userAccount.getBalance()));
    }

    @Override
    public void getCurrentBalance() throws SessionLoginException {
        if(!authenticationProvider.isAuthenticated()){
            throw new SessionLoginException(messageProvider.get("error.force.access"));
        }

        User currentUser = authenticationProvider.getAuthenticatedUser();
        Account userAccount = accountRepository.findByUserId(currentUser.getId());
        BigDecimal currentBalance = userAccount.getBalance();
        System.out.println(messageProvider.get("display.balance", currentBalance));

        Account debtAccount = userAccount.getDebtAccount();
        if(debtAccount != null){
            System.out.println(messageProvider.get("display.debt", debtAccount.getBalance(), debtAccount.getUser().getName()));
        }

        Account receiveableAccount = userAccount.getReceivableAccount();
        if(receiveableAccount != null){
            System.out.println(messageProvider.get("display.receive", receiveableAccount.getBalance(), receiveableAccount.getUser().getName()));
        }
    }

    @Override
    public void transfer(String targetUser, BigDecimal transferAmount) throws SessionLoginException, TransactionException {
       if(!authenticationProvider.isAuthenticated()){
           throw new SessionLoginException(messageProvider.get("error.force.access"));
       }

       User user =  userRepository.findByUserName(targetUser);
       if(user == null){
           throw new TransactionException(messageProvider.get("error.user.not.found"));
       }
       Account targetAccount = accountRepository.findByUserId(user.getId());

       User currentUser = authenticationProvider.getAuthenticatedUser();
       Account sourceAccount = accountRepository.findByUserId(currentUser.getId());

        if(targetAccount.getUser().getName().equals(sourceAccount.getUser().getName())){
            throw new TransactionException(messageProvider.get("error.invalid.transfer.to.own.account"));
        }

       Account receiveableAccount = sourceAccount.getReceivableAccount();
       boolean hasReceiveableAccount = receiveableAccount != null && receiveableAccount.getAccountId().equals(targetAccount.getAccountId());

       AbstractTransferService transferService = null;
       if(hasReceiveableAccount){
           transferService = new ReceivableTransferServiceImpl(accountService, accountRepository, userRepository, authenticationProvider, messageProvider);
       }
       else {
           transferService = new NormalTransferServiceImpl(accountService, accountRepository, userRepository, authenticationProvider, messageProvider);
       }
       transferService.transfer(targetUser, transferAmount);
    }
}
