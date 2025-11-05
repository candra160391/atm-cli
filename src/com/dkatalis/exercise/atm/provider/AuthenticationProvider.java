package com.dkatalis.exercise.atm.provider;

import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.model.User;

public interface AuthenticationProvider {
    public void login(String username) throws SessionLoginException;
    public void logout() throws SessionLoginException;
    public boolean isAuthenticated();
    public User getAuthenticatedUser();
}
