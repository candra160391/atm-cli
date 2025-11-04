package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.TransactionService;

public class BalanceCommand implements MenuCommand {

    TransactionService transactionService;

    public BalanceCommand(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        transactionService.getCurrentBalance();
    }
}
