package com.dktalis.exercise.atm.provider;

public interface AuthenticationProvider {
    public void login(String username);
    public void logout();
    public boolean isAuthenticated();
}
