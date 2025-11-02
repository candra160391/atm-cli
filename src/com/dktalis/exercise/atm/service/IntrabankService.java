package com.dktalis.exercise.atm.service;

import java.math.BigDecimal;

public interface IntrabankService extends TransactionService {
    public void deposit(BigDecimal amount) throws Exception;
    public void withdraw(BigDecimal amount) throws Exception;
    public void getCurrentBalance() throws Exception;
}
