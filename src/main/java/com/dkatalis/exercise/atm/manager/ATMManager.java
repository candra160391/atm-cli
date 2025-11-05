package com.dkatalis.exercise.atm.manager;

import com.dkatalis.exercise.atm.command.MenuCommand;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.ErrorProvider;
import com.dkatalis.exercise.atm.provider.InputProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.provider.impl.ConsoleErrorProvider;
import com.dkatalis.exercise.atm.provider.impl.ConsoleInputProvider;
import com.dkatalis.exercise.atm.provider.impl.ResourceBundleMessageProvider;
import com.dkatalis.exercise.atm.provider.impl.UsernameAuthProvider;
import com.dkatalis.exercise.atm.repository.AccountRepository;
import com.dkatalis.exercise.atm.repository.UserRepository;
import com.dkatalis.exercise.atm.repository.impl.InMemoryAccountRepoImpl;
import com.dkatalis.exercise.atm.repository.impl.InMemoryUserRepoImpl;
import com.dkatalis.exercise.atm.service.AccountService;
import com.dkatalis.exercise.atm.service.TransactionService;
import com.dkatalis.exercise.atm.service.UserService;
import com.dkatalis.exercise.atm.service.impl.AccountServiceImpl;
import com.dkatalis.exercise.atm.service.impl.TransactionServiceImpl;
import com.dkatalis.exercise.atm.service.impl.UserServiceImpl;
import com.dkatalis.exercise.atm.util.MenuUtil;

public class ATMManager {

    InputProvider inputProvider;
    MenuManager menuManager;
    AuthenticationProvider authenticationProvider;
    MessageProvider messageProvider;
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
        this.messageProvider = new ResourceBundleMessageProvider();
        this.authenticationProvider = new UsernameAuthProvider(sessionManager, userRepository, accountRepository, userService, accountService, messageProvider);
        this.errorProvider = new ConsoleErrorProvider(messageProvider);
        this.inputProvider = new ConsoleInputProvider();
        this.transactionService = new TransactionServiceImpl(authenticationProvider, messageProvider, accountRepository, userRepository, accountService);
        this.menuManager = new MenuManager(authenticationProvider, messageProvider, transactionService, inputProvider);
    }

    public void runInteractive(){
        while(true){
            try {
                displayPrompt();
                String userInput = inputProvider.readInput().trim();
                String [] commandArgs = userInput.trim().split("\\s+");
                MenuUtil.validateUserInput(commandArgs, authenticationProvider, messageProvider);
                MenuCommand menuCommand = menuManager.getMenu(commandArgs[0]);
                menuCommand.execute(userInput);
                System.out.println();
            }
            catch (Exception e){
                errorProvider.handle(e);
            }
        }
    }
    public void runOnce(String command){
        try {
            displayPrompt();
            String [] commandArgs = command.trim().split("\\s+");
            MenuUtil.validateUserInput(commandArgs, authenticationProvider, messageProvider);
            MenuCommand menuCommand = menuManager.getMenu(commandArgs[0]);
            menuCommand.execute(command);
            System.out.println();
        }
        catch (Exception e){
            errorProvider.handle(e);
        }
    }

    public AuthenticationProvider getAuthenticationProvider(){
        return authenticationProvider;
    }

    public UserRepository getUserRepository(){
        return userRepository;
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
