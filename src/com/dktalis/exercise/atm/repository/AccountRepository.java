package com.dktalis.exercise.atm.repository;

import com.dktalis.exercise.atm.model.Account;
import java.math.BigDecimal;

public interface AccountRepository {
    public Account findByUserId(String userId);
    public Account addAccount(String userId);
    public void updateBalance(String accountId, BigDecimal newBalance);
}
