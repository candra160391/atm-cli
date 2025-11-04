package com.dktalis.exercise.atm.service;

import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.exception.TransactionException;

import java.math.BigDecimal;

public interface TransactionService {
    public void transfer(String targetUser, BigDecimal transferAmount) throws SessionLoginException, TransactionException;
    public void deposit(BigDecimal amount) throws SessionLoginException, TransactionException;
    public void withdraw(BigDecimal amount) throws SessionLoginException, TransactionException;
    public void getCurrentBalance() throws SessionLoginException;
}
