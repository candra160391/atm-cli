package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.command.MenuCommand;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.provider.ErrorProvider;
import com.dktalis.exercise.atm.provider.InputProvider;
import com.dktalis.exercise.atm.provider.impl.ConsoleErrorProvider;
import com.dktalis.exercise.atm.provider.impl.ConsoleInputProvider;
import com.dktalis.exercise.atm.provider.impl.UsernameAuthProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.repository.impl.InMemoryAccountRepoImpl;
import com.dktalis.exercise.atm.repository.impl.InMemoryUserRepoImpl;
import com.dktalis.exercise.atm.service.AccountService;
import com.dktalis.exercise.atm.service.TransactionService;
import com.dktalis.exercise.atm.service.UserService;
import com.dktalis.exercise.atm.service.impl.AccountServiceImpl;
import com.dktalis.exercise.atm.service.impl.TransactionServiceImpl;
import com.dktalis.exercise.atm.service.impl.UserServiceImpl;
import com.dktalis.exercise.atm.util.MenuUtil;

public class ATMManager {

    InputProvider inputProvider;
    MenuManager menuManager;
    AuthenticationProvider authenticationProvider;
    ErrorProvider errorProvider;
    UserRepository userRepository;
    AccountRepository accountRepository;
    TransactionService transactionService;
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
        this.errorProvider = new ConsoleErrorProvider();
        this.inputProvider = new ConsoleInputProvider();
        this.transactionService = new TransactionServiceImpl(authenticationProvider, accountRepository, userRepository, accountService);
        this.menuManager = new MenuManager(authenticationProvider, transactionService, inputProvider);
    }

    public void run(){
        while(true){
            try {
                displayPrompt();
                String userInput = inputProvider.readInput();
                String [] commandArgs = userInput.trim().split("\\s+");
                MenuUtil.validateUserInput(commandArgs, authenticationProvider);
                MenuCommand menuCommand = menuManager.getMenu(commandArgs[0]);
                menuCommand.execute(userInput);
                System.out.println();
            }
            catch (Exception e){
                errorProvider.handle(e);
            }
        }
    }

    private void displayPrompt() {
        if(authenticationProvider.isAuthenticated()){
            User authenticatedUser = authenticationProvider.getAuthenticatedUser();
            System.out.print(authenticatedUser.getName() + " [" +  authenticatedUser.getId() + "] > ");
        }
        else {
            System.out.print("[type command]" + " > ");
        }
    }
}
