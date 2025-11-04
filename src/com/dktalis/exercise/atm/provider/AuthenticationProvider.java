package com.dktalis.exercise.atm.provider;

import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.model.User;

public interface AuthenticationProvider {
    public void login(String username) throws SessionLoginException;
    public void logout() throws SessionLoginException;
    public boolean isAuthenticated();
    public User getAuthenticatedUser();
}
