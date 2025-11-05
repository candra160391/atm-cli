package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.provider.InputProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;

public class ExitCommand implements MenuCommand {

    private final InputProvider inputProvider;
    private final MessageProvider messageProvider;

    public ExitCommand(InputProvider inputProvider, MessageProvider messageProvider){
        this.inputProvider = inputProvider;
        this.messageProvider = messageProvider;
    }

    @Override
    public void execute(String commandArgs) {
        System.out.println(messageProvider.get("app.goodbye"));
        inputProvider.close();
        System.exit(0);
    }
}
