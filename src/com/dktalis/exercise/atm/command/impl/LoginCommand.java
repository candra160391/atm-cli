package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;

public class LoginCommand implements MenuCommand {

    public AuthenticationProvider authenticationProvider;

    public LoginCommand(AuthenticationProvider authenticationProvider){
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void execute(String commandArgs) throws SessionLoginException {
        String username = commandArgs.replace("login","").trim();
        authenticationProvider.login(username);
    }
}
