package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;

public class LogoutCommand implements MenuCommand {


    private final AuthenticationProvider authenticationProvider;

    public LogoutCommand(AuthenticationProvider authenticationProvider, MessageProvider messageProvider){
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void execute(String commandArgs) throws SessionLoginException {
        authenticationProvider.logout();
    }
}
