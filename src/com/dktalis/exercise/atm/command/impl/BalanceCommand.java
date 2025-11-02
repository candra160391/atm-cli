package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.service.IntrabankService;

public class BalanceCommand implements MenuCommand {

    IntrabankService intrabankService;

    public BalanceCommand(IntrabankService intrabankService){
        this.intrabankService = intrabankService;
    }

    @Override
    public void execute(String commandArgs) throws Exception {
        intrabankService.getCurrentBalance();
    }
}
