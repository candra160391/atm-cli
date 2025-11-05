package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class DepositCommand implements MenuCommand {

    private final TransactionService transactionService;

    public DepositCommand(TransactionService transactionService, MessageProvider messageProvider){
        this.transactionService = transactionService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        BigDecimal amount = new BigDecimal(commands[1]);
        transactionService.deposit(amount);
    }
}
