package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;

public class LogoutCommand implements MenuCommand {


    public AuthenticationProvider authenticationProvider;

    public LogoutCommand(AuthenticationProvider authenticationProvider){
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void execute(String commandArgs) {
        authenticationProvider.logout();
    }
}
