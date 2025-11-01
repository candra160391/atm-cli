package com.dktalis.exercise.atm.provider.impl;

import com.dktalis.exercise.atm.manager.SessionManager;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;

public class UsernameAuthProvider implements AuthenticationProvider {

    public UserRepository userRepository;
    public AccountRepository accountRepository;
    public SessionManager sessionManager;

    public UsernameAuthProvider
    (
        SessionManager sessionManager,
        UserRepository userRepository,
        AccountRepository accountRepository
    )
    {
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public boolean isAuthenticated(){
        return sessionManager.getCurrentUser() != null;
    }

    public User getAuthenticatedUser(){
        return sessionManager.getCurrentUser();
    }

    @Override
    public void login(String username) {
         User user = userRepository.findByUserName(username);
         if(user == null){
            user = userRepository.addUser(username);
            accountRepository.addAccount(user.getId());
         }
         sessionManager.createSession(user);

        System.out.println("Hi " + getAuthenticatedUser().getName());
    }

    @Override
    public void logout() {
        User currentUser = getAuthenticatedUser();
        sessionManager.clearSession();
        System.out.println("Goodbye " + currentUser.getName());

    }
}
