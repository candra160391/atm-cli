package com.dktalis.exercise.atm.service.impl;

import com.dktalis.exercise.atm.Enum.TransactionTypeEnum;
import com.dktalis.exercise.atm.dto.TransferAdjusment;
import com.dktalis.exercise.atm.exception.TransactionException;
import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.provider.AuthenticationProvider;
import com.dktalis.exercise.atm.repository.AccountRepository;
import com.dktalis.exercise.atm.repository.UserRepository;
import com.dktalis.exercise.atm.service.AbstractTransferService;
import com.dktalis.exercise.atm.service.AccountService;
import java.math.BigDecimal;

public class NormalTransferServiceImpl extends AbstractTransferService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;

    public NormalTransferServiceImpl
    (
        AccountService accountService,
        AccountRepository accountRepository,
        UserRepository userRepository,
        AuthenticationProvider authenticationProvider
    )
    {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Account getSourceAccount() {
        User currentUser = authenticationProvider.getAuthenticatedUser();
        return accountRepository.findByUserId(currentUser.getId());
    }

    @Override
    public AccountService getAccountService() {
        return accountService;
    }

    @Override
    public Account getTargetAccount(String username) {
        User user =  userRepository.findByUserName(username);
        if(user == null){
            return null;
        }
        return accountRepository.findByUserId(user.getId());
    }

    @Override
    public void validateTransfer(Account sourceAccount, Account targetAccount) throws TransactionException {
        BigDecimal sourceAccountBalance = sourceAccount.getBalance();

        if(sourceAccountBalance.compareTo(BigDecimal.ZERO) == 0){
            throw new TransactionException("not enough balance");
        }

        if(sourceAccount.getDebtAccount() != null || sourceAccount.getReceivableAccount() != null){
            throw new TransactionException("invalid transfer type");
        }

        if(targetAccount == null) {
            throw new TransactionException("user not found");
        }
    }

    @Override
    public TransferAdjusment doBeforeTransfer(Account sourceAccount, Account targetAccount, BigDecimal transferAmount) throws TransactionException{

        if(transferAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new TransactionException("invalid transfer amount");
        }

        BigDecimal debtAmount = BigDecimal.ZERO;
        BigDecimal sourceAccountBalance = sourceAccount.getBalance();

        if(sourceAccountBalance.compareTo(transferAmount) <= 0){
            debtAmount = transferAmount.subtract(sourceAccountBalance);
            transferAmount = sourceAccountBalance;
        }

        TransferAdjusment transferAdjusment = new TransferAdjusment();
        transferAdjusment.setHasDebt(debtAmount.compareTo(BigDecimal.ZERO) > 0);
        transferAdjusment.setHasReceivable(false);
        transferAdjusment.setDebtAmount(debtAmount);
        transferAdjusment.setDebtTransactionType(TransactionTypeEnum.CREDIT);
        transferAdjusment.setTargetAmount(transferAmount);
        transferAdjusment.setTargetTransactionType(TransactionTypeEnum.CREDIT);
        transferAdjusment.setSourceAmount(transferAmount);
        transferAdjusment.setSourceTransactionType(TransactionTypeEnum.DEBET);
        return transferAdjusment;
    }
}
