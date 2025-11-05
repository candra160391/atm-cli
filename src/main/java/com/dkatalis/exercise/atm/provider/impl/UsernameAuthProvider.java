package com.dkatalis.exercise.atm.provider.impl;

import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.manager.SessionManager;
import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.repository.AccountRepository;
import com.dkatalis.exercise.atm.repository.UserRepository;
import com.dkatalis.exercise.atm.service.AccountService;
import com.dkatalis.exercise.atm.service.UserService;

public class UsernameAuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final UserService userService;
    private final AccountService accountService;
    private final MessageProvider messageProvider;

    public UsernameAuthProvider
    (
        SessionManager sessionManager,
        UserRepository userRepository,
        AccountRepository accountRepository,
        UserService userService,
        AccountService accountService,
        MessageProvider messageProvider
    )
    {
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.accountService = accountService;
        this.messageProvider = messageProvider;
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
            throw new SessionLoginException(messageProvider.get("error.multiple.login"));
        }

        else if(isAuthenticated()){
            throw new SessionLoginException(messageProvider.get("error.same.login"));
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
        System.out.println(messageProvider.get("auth.login.success", userAccount.getUser().getName()));
        System.out.println(messageProvider.get("display.balance", userAccount.getBalance()));

        Account debtAccount = userAccount.getDebtAccount();
        if(debtAccount != null)
            System.out.println(messageProvider.get("display.debt", debtAccount.getBalance(), debtAccount.getUser().getName()));

        Account receivableAccount = userAccount.getReceivableAccount();
        if(receivableAccount != null)
            System.out.println(messageProvider.get("display.receive", receivableAccount.getBalance(), receivableAccount.getUser().getName()));

    }

    @Override
    public void logout() throws SessionLoginException {
        if(!isAuthenticated()){
            throw new SessionLoginException(messageProvider.get("error.already.logout"));
        }
        else {
            User currentUser = getAuthenticatedUser();
            sessionManager.clearSession();
            System.out.println(messageProvider.get("auth.logout.success", currentUser.getName()));
        }
    }
}
