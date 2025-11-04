package com.dktalis.exercise.atm.provider.impl;

import com.dktalis.exercise.atm.exception.InvalidCommandException;
import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.exception.TransactionException;
import com.dktalis.exercise.atm.provider.ErrorProvider;

public class ConsoleErrorProvider implements ErrorProvider {
    @Override
    public void handle(Exception e) {
        if(e instanceof InvalidCommandException){
            System.out.println("[CLI Exception]: " + e.getMessage());
            System.out.println();
        }
        else if(e instanceof SessionLoginException){
            System.out.println("[Session Exception]: " + e.getMessage());
            System.out.println();
        }
        else if(e instanceof TransactionException){
            System.out.println("[Transaction Exception]: " + e.getMessage());
            System.out.println();
        }
        else {
            System.out.println("[System Error]: ");
            e.printStackTrace();
            System.out.println();
        }
    }
}
