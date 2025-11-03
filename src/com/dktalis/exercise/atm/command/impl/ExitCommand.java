package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.provider.InputProvider;

public class ExitCommand implements MenuCommand {

    public InputProvider inputProvider;

    public ExitCommand(InputProvider inputProvider){
        this.inputProvider = inputProvider;
    }

    @Override
    public void execute(String commandArgs) {
        System.out.println("Thank ypu for using this ATM. Good bye");
        inputProvider.close();
        System.exit(0);
    }
}
