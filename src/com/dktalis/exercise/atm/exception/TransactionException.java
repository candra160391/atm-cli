package com.dktalis.exercise.atm.exception;

public class TransactionException extends Exception{
    public TransactionException(String errorMessage){
        super(errorMessage);
    }
}
