package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.Enum.MenuEnum;
import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.provider.InputProvider;
import com.dktalis.exercise.atm.provider.impl.ConsoleInputProvider;
import com.dktalis.exercise.atm.provider.impl.UsernameAuthProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.repository.impl.InMemoryAccountRepoImpl;
import com.dktalis.exercise.atm.repository.impl.InMemoryUserRepoImpl;
import com.dktalis.exercise.atm.service.AccountService;
import com.dktalis.exercise.atm.service.IntrabankService;
import com.dktalis.exercise.atm.service.UserService;
import com.dktalis.exercise.atm.service.impl.AccountServiceImpl;
import com.dktalis.exercise.atm.service.impl.IntrabankServiceImpl;
import com.dktalis.exercise.atm.service.impl.UserServiceImpl;
import com.dktalis.exercise.atm.util.MenuUtil;

public class ATMManager {

    InputProvider inputProvider;
    MenuManager menuManager;
    AuthenticationProvider authenticationProvider;
    UserRepository userRepository;
    AccountRepository accountRepository;
    IntrabankService intrabankService;
    UserService userService;
    AccountService accountService;
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
        this.userService = new UserServiceImpl(userRepository);
        this.accountService = new AccountServiceImpl(accountRepository);
        this.authenticationProvider = new UsernameAuthProvider(sessionManager, userRepository, accountRepository, userService, accountService);
        this.inputProvider = new ConsoleInputProvider();
        this.intrabankService = new IntrabankServiceImpl(authenticationProvider, accountRepository, userRepository, accountService);
        this.menuManager = new MenuManager(authenticationProvider, intrabankService, inputProvider);
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
