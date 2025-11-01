package com.dktalis.exercise.atm.service;

public interface IntrabankService extends TransactionService {
    public void deposit();
    public void withdraw();
}
