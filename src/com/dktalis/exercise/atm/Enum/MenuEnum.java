package com.dktalis.exercise.atm.Enum;

public enum MenuEnum {
    LOGIN("login"),
    TRANSFER("transfer"),
    WITHDRAW("withdraw"),
    BALANCE("balance"),
    DEPOSIT("deposit"),
    LOGOUT("logout"),
    EXIT("exit"),
    HELP("help");

    final String value;

    MenuEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
