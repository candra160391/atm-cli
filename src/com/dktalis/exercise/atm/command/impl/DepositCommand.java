package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.IntrabankService;
import java.math.BigDecimal;

public class DepositCommand implements MenuCommand {

    IntrabankService intrabankService;

    public DepositCommand(IntrabankService intrabankService){
        this.intrabankService = intrabankService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        String [] commands = commandArgs.split(" ");
        BigDecimal amount = new BigDecimal(commands[1]);
        intrabankService.deposit(amount);
    }
}
