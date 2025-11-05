package com.dkatalis.exercise.atm.Enum;

public enum TransactionTypeEnum {
    DEBET("debet"),
    CREDIT("credit");

    final String value;

    TransactionTypeEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
