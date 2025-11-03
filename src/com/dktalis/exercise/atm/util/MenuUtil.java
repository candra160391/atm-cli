package com.dktalis.exercise.atm.util;

import com.dktalis.exercise.atm.Enum.MenuEnum;

import java.util.Objects;

public class MenuUtil{

    public static void validateUserInput(String...commands) throws Exception {

        if(commands == null){
            throw new Exception("command should not be blank");
        }

        if(commands[0].equals(MenuEnum.LOGIN.getValue())){
            if(commands.length == 1){
                throw new Exception("invalid login command. Valid command is: login [username]");
            }
        }
        else if(commands[0].equals(MenuEnum.TRANSFER.getValue())){
            if(commands.length < 3){
                throw new Exception("invalid transfer command. Valid command is: transfer [target user] [amount]");
            }
        }
        else if(commands[0].equals(MenuEnum.DEPOSIT.getValue())){
            if(commands.length < 2){
                throw new Exception("invalid deposit command. Valid command is: deposit [amount]");
            }
        }
        else if(commands[0].equals(MenuEnum.WITHDRAW.getValue())){
            if(commands.length < 2){
                throw new Exception("invalid withdraw command. Valid command is: withdraw [amount]");
            }
        }
        else if(commands[0].equals(MenuEnum.BALANCE.getValue())){
            if(commands.length > 1){
                throw new Exception("invalid balance command. Valid command is: balance");
            }
        }
        else if(commands[0].equals(MenuEnum.LOGOUT.getValue())){
            if(commands.length > 1){
                throw new Exception("invalid logout command. Valid command is: logout");
            }
        }
        else if(commands[0].equals(MenuEnum.EXIT.getValue())){
            if(commands.length > 1){
                throw new Exception("invalid exit command. Valid command is: exit");
            }
        }
        else {
            throw new Exception("Unknown command");
        }
    }
}
