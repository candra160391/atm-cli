package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.IntrabankService;

import java.math.BigDecimal;

public class TransferCommand implements MenuCommand {

    IntrabankService intrabankService;

    public TransferCommand(IntrabankService intrabankService){
        this.intrabankService = intrabankService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        String targetUser = commands[1];
        BigDecimal transferAmount = new BigDecimal(commands[2]);
        intrabankService.transfer(targetUser, transferAmount);
    }
}
