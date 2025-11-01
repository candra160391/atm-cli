package com.dktalis.exercise.atm.provider;

import com.dktalis.exercise.atm.model.User;

public interface AuthenticationProvider {
    public void login(String username);
    public void logout();
    public boolean isAuthenticated();
    public User getAuthenticatedUser();
}
