package com.dkatalis.exercise.atm.command.impl;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.provider.MessageProvider;

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
