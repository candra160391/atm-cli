package com.dkatalis.exercise.atm.service;

import com.dkatalis.exercise.atm.exception.SessionLoginException;
import com.dkatalis.exercise.atm.exception.TransactionException;

import java.math.BigDecimal;

public interface TransactionService {
    public void transfer(String targetUser, BigDecimal transferAmount) throws SessionLoginException, TransactionException;
    public void deposit(BigDecimal amount) throws SessionLoginException, TransactionException;
    public void withdraw(BigDecimal amount) throws SessionLoginException, TransactionException;
    public void getCurrentBalance() throws SessionLoginException;
}
