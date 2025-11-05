package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.service.TransactionService;

import java.math.BigDecimal;

public class WithdrawCommand implements MenuCommand {

    private final TransactionService transactionService;

    public WithdrawCommand(TransactionService intrabankService, MessageProvider messageProvider){
        this.transactionService = intrabankService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        BigDecimal amount = new BigDecimal(commands[1]);
        transactionService.withdraw(amount);
    }
}
