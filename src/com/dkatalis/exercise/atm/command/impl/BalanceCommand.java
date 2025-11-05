package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.service.TransactionService;

public class BalanceCommand implements MenuCommand {

    private final TransactionService transactionService;

    public BalanceCommand(TransactionService transactionService, MessageProvider messageProvider){
        this.transactionService = transactionService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        transactionService.getCurrentBalance();
    }
}
