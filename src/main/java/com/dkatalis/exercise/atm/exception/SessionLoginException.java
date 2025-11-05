package com.dkatalis.exercise.atm.exception;

public class SessionLoginException extends Exception{
    public SessionLoginException(String errorMessage){
        super(errorMessage);
    }
}
