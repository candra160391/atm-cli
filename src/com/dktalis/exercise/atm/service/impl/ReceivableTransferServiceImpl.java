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

public class ReceivableTransferServiceImpl extends AbstractTransferService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;

    public ReceivableTransferServiceImpl
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

        if(sourceAccount.getReceivableAccount() == null){
            throw new TransactionException("invalid transfer type");
        }

        if(targetAccount == null) {
            throw new TransactionException("user not found");
        }
    }

    @Override
    public TransferAdjusment doBeforeTransfer(Account sourceAccount, Account targetAccount, BigDecimal transferAmount) throws TransactionException {

        if(transferAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new TransactionException("invalid transfer amount");
        }

        BigDecimal receiveableAmount = sourceAccount.getReceivableAccount().getBalance();
        BigDecimal residualValue = BigDecimal.ZERO;
        BigDecimal sourceAccountBalance = sourceAccount.getBalance();

        // scenario
        // transfer < debt
        // transfer == deb
        // transfer > deb, debt is paid, residual = transfer - deb, then residual < balance , transfer amount = residual
        // transfer > deb , debt is paid, residual = transfer - deb, then residual > balance, transfer amount = balance, debt = balance - residual

        if(transferAmount.compareTo(receiveableAmount) <= 0){
            return getTransferAdjusment(BigDecimal.ZERO, transferAmount, BigDecimal.ZERO);
        }

        residualValue = transferAmount.subtract(receiveableAmount);

        if(residualValue.compareTo(sourceAccountBalance) < 0){
            return getTransferAdjusment(BigDecimal.ZERO, receiveableAmount, residualValue);
        }
        else {
            BigDecimal debtAmount = transferAmount.subtract(sourceAccountBalance).subtract(receiveableAmount);
            return getTransferAdjusment(debtAmount, receiveableAmount, sourceAccountBalance);
        }
    }

    private TransferAdjusment getTransferAdjusment(BigDecimal debtAmount, BigDecimal receivableAmount, BigDecimal transferAmount) {
        TransferAdjusment response = new TransferAdjusment();
        response.setHasDebt(false);
        response.setHasReceivable(true);
        response.setReceivableAmount(receivableAmount);
        response.setReceiveTransactionType(TransactionTypeEnum.DEBET);
        response.setDebtAmount(debtAmount);
        response.setDebtTransactionType(TransactionTypeEnum.DEBET);
        response.setSourceAmount(transferAmount);
        response.setSourceTransactionType(TransactionTypeEnum.DEBET);
        response.setTargetAmount(transferAmount);
        response.setTargetTransactionType(TransactionTypeEnum.CREDIT);
        return response;
    }

}
