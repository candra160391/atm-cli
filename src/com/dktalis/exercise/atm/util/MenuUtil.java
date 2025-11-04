package com.dktalis.exercise.atm.util;

import com.dktalis.exercise.atm.Enum.MenuEnum;
import com.dktalis.exercise.atm.exception.InvalidCommandException;
import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuUtil{

    public static void validateUserInput(String[] commands, AuthenticationProvider authenticationProvider) throws SessionLoginException, InvalidCommandException {

        if(commands == null){
            throw new InvalidCommandException("command should not be blank");
        }

        if(commands[0].equals(MenuEnum.LOGIN.getValue())){
            if(commands.length == 1){
                throw new InvalidCommandException("Invalid command. Usage: login [username]");
            }
        }
        else if(commands[0].equals(MenuEnum.TRANSFER.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException("please login first");
            }
            if(commands.length != 3){
                throw new InvalidCommandException("invalid command. Usage: transfer [username target] [amount]");
            }

            try {
                new BigDecimal(commands[2]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException("Invalid amount format");
            }
        }
        else if(commands[0].equals(MenuEnum.DEPOSIT.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException("please login first");
            }
            if(commands.length != 2){
                throw new InvalidCommandException("invalid command. Usage: deposit [amount]");
            }
            try {
                new BigDecimal(commands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException("Invalid amount format");
            }
        }
        else if(commands[0].equals(MenuEnum.WITHDRAW.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException("please login first");
            }
            if(commands.length != 2){
                throw new InvalidCommandException("invalid command. Usage: withdraw [amount]");
            }
            try {
                new BigDecimal(commands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException("Invalid amount format");
            }

        }
        else if(commands[0].equals(MenuEnum.BALANCE.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException("please login first");
            }
            if(commands.length != 1){
                throw new InvalidCommandException("invalid command. Usage: balance");
            }
        }
        else if(commands[0].equals(MenuEnum.LOGOUT.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException("please login first");
            }
            if(commands.length != 1){
                throw new InvalidCommandException("invalid command. Usage: logout");
            }
        }
        else if(commands[0].equals(MenuEnum.EXIT.getValue())){
            if(commands.length != 1){
                throw new InvalidCommandException("invalid command. Usage: exit");
            }
        }
        else if(commands[0].equals(MenuEnum.HELP.getValue())){
            if(commands.length != 1){
                throw new InvalidCommandException("invalid command. Usage: help");
            }
        }
        else {
            throw new InvalidCommandException("Unknown command. Type help for usage");
        }
    }
}
