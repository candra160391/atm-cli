package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class DepositCommand implements MenuCommand {

    TransactionService transactionService;

    public DepositCommand(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        BigDecimal amount = new BigDecimal(commands[1]);
        transactionService.deposit(amount);
    }
}
