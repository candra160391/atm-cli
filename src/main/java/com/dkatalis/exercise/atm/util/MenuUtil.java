package com.dkatalis.exercise.atm.util;

import com.dkatalis.exercise.atm.Enum.MenuEnum;
import com.dkatalis.exercise.atm.exception.InvalidCommandException;
import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;

import java.math.BigDecimal;

public class MenuUtil{

    public static void validateUserInput(String[] commands, AuthenticationProvider authenticationProvider, MessageProvider messageProvider) throws SessionLoginException, InvalidCommandException {

        if(commands == null){
            throw new InvalidCommandException(messageProvider.get("error.blank.command"));
        }

        if(commands[0].equals(MenuEnum.LOGIN.getValue())){
            if(commands.length == 1){
                throw new InvalidCommandException(messageProvider.get("error.invalid.login.command"));
            }
        }
        else if(commands[0].equals(MenuEnum.TRANSFER.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException(messageProvider.get("error.force.access"));
            }
            if(commands.length != 3){
                throw new InvalidCommandException(messageProvider.get("error.invalid.transfer.command"));
            }

            try {
                new BigDecimal(commands[2]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException(messageProvider.get("error.invalid.amount.format"));
            }
        }
        else if(commands[0].equals(MenuEnum.DEPOSIT.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException(messageProvider.get("error.force.access"));
            }
            if(commands.length != 2){
                throw new InvalidCommandException(messageProvider.get("error.invalid.deposit.command"));
            }
            try {
                new BigDecimal(commands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException(messageProvider.get("error.invalid.amount.format"));
            }
        }
        else if(commands[0].equals(MenuEnum.WITHDRAW.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException(messageProvider.get("error.force.access"));
            }
            if(commands.length != 2){
                throw new InvalidCommandException(messageProvider.get("error.invalid.withdraw.command"));
            }
            try {
                new BigDecimal(commands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException(messageProvider.get("error.invalid.amount.format"));
            }

        }
        else if(commands[0].equals(MenuEnum.BALANCE.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException(messageProvider.get("error.force.access"));
            }
            if(commands.length != 1){
                throw new InvalidCommandException(messageProvider.get("error.invalid.balance.command"));
            }
        }
        else if(commands[0].equals(MenuEnum.LOGOUT.getValue())){
            if(!authenticationProvider.isAuthenticated()){
                throw new SessionLoginException(messageProvider.get("error.force.access"));
            }
            if(commands.length != 1){
                throw new InvalidCommandException(messageProvider.get("error.invalid.logout.command"));
            }
        }
        else if(commands[0].equals(MenuEnum.EXIT.getValue())){
            if(commands.length != 1){
                throw new InvalidCommandException(messageProvider.get("error.invalid.exit.command"));
            }
        }
        else if(commands[0].equals(MenuEnum.HELP.getValue())){
            if(commands.length != 1){
                throw new InvalidCommandException(messageProvider.get("error.invalid.help.command"));
            }
        }
        else {
            throw new InvalidCommandException(messageProvider.get("error.unknown.command"));
        }
    }
}
