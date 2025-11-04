package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class WithdrawCommand implements MenuCommand {

    TransactionService transactionService;

    public WithdrawCommand(TransactionService intrabankService){
        this.transactionService = intrabankService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        BigDecimal amount = new BigDecimal(commands[1]);
        transactionService.withdraw(amount);
    }
}
