package com.dktalis.exercise.atm.service;

import java.math.BigDecimal;

public interface TransactionService {
    public void transfer(String targetUser, BigDecimal transferAmount) throws Exception;
}
