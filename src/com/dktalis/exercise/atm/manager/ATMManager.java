package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.provider.InputProvider;
import com.dktalis.exercise.atm.provider.impl.ConsoleInputProvider;
import com.dktalis.exercise.atm.provider.impl.UsernameAuthProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.repository.impl.InMemoryAccountRepoImpl;
import com.dktalis.exercise.atm.repository.impl.InMemoryUserRepoImpl;
import com.dktalis.exercise.atm.service.IntrabankService;
import com.dktalis.exercise.atm.service.impl.IntrabankServiceImpl;
import com.dktalis.exercise.atm.util.MenuUtil;

public class ATMManager {

    InputProvider inputProvider;
    MenuManager menuManager;
    AuthenticationProvider authenticationProvider;
    UserRepository userRepository;
    AccountRepository accountRepository;
    IntrabankService intrabankService;
    UserManager userManager;
    AccountManager accountManager;
    SessionManager sessionManager;

    public ATMManager()
    {
        this.userManager = new UserManager();
        this.accountManager = new AccountManager();
        this.sessionManager = new SessionManager();

        this.userRepository = new InMemoryUserRepoImpl(userManager);
        this.accountRepository = new InMemoryAccountRepoImpl(accountManager);
        this.authenticationProvider = new UsernameAuthProvider(sessionManager, userRepository, accountRepository);
        this.inputProvider = new ConsoleInputProvider();
        this.intrabankService = new IntrabankServiceImpl(authenticationProvider, accountRepository);
        this.menuManager = new MenuManager(authenticationProvider, intrabankService);
    }

    public void run(){
        while(true){
            try {
                String userInput = inputProvider.readInput();
                String [] commandArgs = userInput.split(" ");
                MenuUtil.validateUserInput(commandArgs);
                MenuCommand menuCommand = menuManager.getMenu(commandArgs[0]);
                menuCommand.execute(userInput);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
