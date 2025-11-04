package com.dktalis.exercise.atm.provider.impl;

import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.manager.SessionManager;
import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.service.AccountService;
import com.dktalis.exercise.atm.service.UserService;

public class UsernameAuthProvider implements AuthenticationProvider {

    public UserRepository userRepository;
    public AccountRepository accountRepository;
    public SessionManager sessionManager;
    public UserService userService;
    public AccountService accountService;

    public UsernameAuthProvider
    (
        SessionManager sessionManager,
        UserRepository userRepository,
        AccountRepository accountRepository,
        UserService userService,
        AccountService accountService
    )
    {
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.accountService = accountService;
    }

    public boolean isAuthenticated(){
        return sessionManager.getCurrentUser() != null;
    }

    public User getAuthenticatedUser(){
        return sessionManager.getCurrentUser();
    }

    @Override
    public void login(String username) throws SessionLoginException {

        if(isAuthenticated() && !getAuthenticatedUser().getName().equals(username)){
            throw new SessionLoginException("Please logout first");
        }

        else if(isAuthenticated()){
            throw new SessionLoginException("Already logged in");
        }
        else {
            User user = userRepository.findByUserName(username);
            Account account ;
            if(user == null){
                user = userService.addUser(username);
                account = accountService.addAccount(user);
            }
            else {
                account = accountRepository.findByUserId(user.getId());
            }

            sessionManager.createSession(user);
            displayWelcome(account);
        }

    }

    private void displayWelcome(Account userAccount) {
        System.out.println("Hi " + userAccount.getUser().getName());
        System.out.println("Your balance is $" + userAccount.getBalance());

        Account debtAccount = userAccount.getDebtAccount();
        if(debtAccount != null)
            System.out.println("Owed $" + debtAccount.getBalance() + " to " + debtAccount.getUser().getName());

        Account receivableAccount = userAccount.getReceivableAccount();
        if(receivableAccount != null)
            System.out.println("Owed $" + receivableAccount.getBalance() + " from " + receivableAccount.getUser().getName());

    }

    @Override
    public void logout() throws SessionLoginException {
        if(!isAuthenticated()){
            throw new SessionLoginException("already logged out");
        }
        else {
            User currentUser = getAuthenticatedUser();
            sessionManager.clearSession();
            System.out.println("Goodbye " + currentUser.getName());
        }
    }
}
