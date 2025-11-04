package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.command.impl.*;
import com.dktalis.exercise.atm.exception.InvalidCommandException;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.provider.InputProvider;
import com.dktalis.exercise.atm.service.TransactionService;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    Map<String, MenuCommand> menuCommand = new HashMap<>();
    public MenuManager
    (
        AuthenticationProvider authenticationProvider,
        TransactionService transactionService,
        InputProvider inputProvider
    ){
        menuCommand.put("login", new LoginCommand(authenticationProvider));
        menuCommand.put("deposit", new DepositCommand(transactionService));
        menuCommand.put("withdraw", new WithdrawCommand(transactionService));
        menuCommand.put("transfer", new TransferCommand(transactionService));
        menuCommand.put("balance", new BalanceCommand(transactionService));
        menuCommand.put("logout", new LogoutCommand(authenticationProvider));
        menuCommand.put("exit", new ExitCommand(inputProvider));
        menuCommand.put("help", new HelpCommand());
    }

    public MenuCommand getMenu(String menu) throws InvalidCommandException {
        MenuCommand command = menuCommand.get(menu);
        if(command == null){
            throw new InvalidCommandException("menu not found");
        }
        return command;
    }
}
