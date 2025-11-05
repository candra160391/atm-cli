package com.dkatalis.exercise.atm.provider.impl;

import com.dkatalis.exercise.atm.exception.InvalidCommandException;
import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.exception.TransactionException;
import com.dkatalis.exercise.atm.provider.ErrorProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;

public class ConsoleErrorProvider implements ErrorProvider {

    private final MessageProvider messageProvider;

    public ConsoleErrorProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @Override
    public void handle(Exception e) {
        if(e instanceof InvalidCommandException){
            System.out.println(messageProvider.get("error.invalid.command", e.getMessage()));
            System.out.println();
        }
        else if(e instanceof SessionLoginException){
            System.out.println(messageProvider.get("error.invalid.session", e.getMessage()));
            System.out.println();
        }
        else if(e instanceof TransactionException){
            System.out.println(messageProvider.get("error.invalid.transaction", e.getMessage()));
            System.out.println();
        }
        else {
            System.out.println(messageProvider.get("error.unknown", e.getMessage()));
            e.printStackTrace();
            System.out.println();
        }
    }
}
