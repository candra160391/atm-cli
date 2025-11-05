package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;

public class LoginCommand implements MenuCommand {

    private final AuthenticationProvider authenticationProvider;
    private final MessageProvider messageProvider;

    public LoginCommand(AuthenticationProvider authenticationProvider, MessageProvider messageProvider){
        this.authenticationProvider = authenticationProvider;
        this.messageProvider = messageProvider;
    }

    @Override
    public void execute(String commandArgs) throws SessionLoginException {
        String username = commandArgs.replace("login","").trim();
        authenticationProvider.login(username);
    }
}
