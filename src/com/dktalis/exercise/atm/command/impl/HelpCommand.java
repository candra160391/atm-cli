package com.dktalis.exercise.atm.command.impl;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.provider.InputProvider;

public class HelpCommand implements MenuCommand {

    @Override
    public void execute(String commandArgs) {
        String commands =
                """
                    login [username]\s
                    transfer [username target] [amount]\s
                    deposit [amount]\s
                    withdraw [amount]\s
                    logout\s
                    logout\s
                    exit""";
        System.out.println("List available command are: \n" + commands);
    }
}
