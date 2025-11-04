package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class TransferCommand implements MenuCommand {

    TransactionService transactionService;

    public TransferCommand(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        String targetUser = commands[1];
        BigDecimal transferAmount = new BigDecimal(commands[2]);
        transactionService.transfer(targetUser, transferAmount);
    }
}
