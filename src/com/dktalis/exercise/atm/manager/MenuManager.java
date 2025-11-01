package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.command.impl.*;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.service.IntrabankService;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    Map<String, MenuCommand> menuCommand = new HashMap<>();
    public MenuManager
    (
        AuthenticationProvider authenticationProvider,
        IntrabankService intrabankService
    ){
        menuCommand.put("login", new LoginCommand(authenticationProvider));
        menuCommand.put("deposit", new DepositCommand(intrabankService));
        menuCommand.put("withdraw", new WithdrawCommand());
        menuCommand.put("transfer", new TransferCommand());
        menuCommand.put("logout", new LogoutCommand(authenticationProvider));
    }

    public MenuCommand getMenu(String menu){
        return menuCommand.get(menu);
    }
}
