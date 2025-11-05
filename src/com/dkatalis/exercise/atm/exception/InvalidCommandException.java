package com.dkatalis.exercise.atm.exception;

public class InvalidCommandException extends Exception{
    public InvalidCommandException(String errorMessage){
        super(errorMessage);
    }
}
