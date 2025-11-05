package com.dkatalis.exercise.atm.manager;

import com.dkatalis.exercise.atm.Enum.MenuEnum;
import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.command.impl.*;
import com.dkatalis.exercise.atm.exception.InvalidCommandException;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.InputProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.service.TransactionService;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    Map<String, MenuCommand> menuCommand = new HashMap<>();
    private final MessageProvider messageProvider;
    public MenuManager
    (
            AuthenticationProvider authenticationProvider,
            MessageProvider messageProvider,
            TransactionService transactionService,
            InputProvider inputProvider
    ){

        this.messageProvider = messageProvider;

        menuCommand.put(MenuEnum.LOGIN.getValue(), new LoginCommand(authenticationProvider, messageProvider));
        menuCommand.put(MenuEnum.DEPOSIT.getValue(), new DepositCommand(transactionService, messageProvider));
        menuCommand.put(MenuEnum.WITHDRAW.getValue(), new WithdrawCommand(transactionService, messageProvider));
        menuCommand.put(MenuEnum.TRANSFER.getValue(), new TransferCommand(transactionService, messageProvider));
        menuCommand.put(MenuEnum.BALANCE.getValue(), new BalanceCommand(transactionService, messageProvider));
        menuCommand.put(MenuEnum.LOGOUT.getValue(), new LogoutCommand(authenticationProvider, messageProvider));
        menuCommand.put(MenuEnum.EXIT.getValue(), new ExitCommand(inputProvider, messageProvider));
        menuCommand.put(MenuEnum.HELP.getValue(), new HelpCommand());
    }

    public MenuCommand getMenu(String menu) throws InvalidCommandException {
        MenuCommand command = menuCommand.get(menu);
        if(command == null){
            throw new InvalidCommandException(messageProvider.get("error.menu.not.found"));
        }
        return command;
    }
}
