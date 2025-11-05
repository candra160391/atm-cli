package com.dkatalis.exercise.atm.service.impl;

import com.dkatalis.exercise.atm.Enum.TransactionTypeEnum;
import com.dkatalis.exercise.atm.dto.TransferAdjusment;
import com.dkatalis.exercise.atm.exception.TransactionException;
import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.repository.AccountRepository;
import com.dkatalis.exercise.atm.repository.UserRepository;
import com.dkatalis.exercise.atm.service.AbstractTransferService;
import com.dkatalis.exercise.atm.service.AccountService;

import java.math.BigDecimal;

public class DebtTransferServiceImpl extends AbstractTransferService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final MessageProvider messageProvider;

    public DebtTransferServiceImpl
    (
            AccountService accountService,
            AccountRepository accountRepository,
            UserRepository userRepository,
            AuthenticationProvider authenticationProvider, MessageProvider messageProvider
    )
    {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.messageProvider = messageProvider;
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

        if(sourceAccount.getDebtAccount() == null){
            throw new TransactionException(messageProvider.get("error.invalid.transfer.type"));
        }

        if(targetAccount == null) {
            throw new TransactionException(messageProvider.get("error.user.not.found"));
        }
    }

    @Override
    public TransferAdjusment doBeforeTransfer(Account sourceAccount, Account targetAccount, BigDecimal transferAmount) throws TransactionException {

        if(transferAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new TransactionException(messageProvider.get("error.invalid.transfer.amount"));
        }

        BigDecimal debtAmount = sourceAccount.getDebtAccount().getBalance();
        BigDecimal residualValue = BigDecimal.ZERO;

        if(transferAmount.compareTo(debtAmount) > 0){
            residualValue = transferAmount.subtract(debtAmount);
            transferAmount = debtAmount;
        }

        TransferAdjusment transferAdjusment = new TransferAdjusment();
        transferAdjusment.setHasDebt(true);
        transferAdjusment.setHasReceivable(false);
        transferAdjusment.setDebtAmount(transferAmount);
        transferAdjusment.setDebtTransactionType(TransactionTypeEnum.DEBET);
        transferAdjusment.setTargetAmount(transferAmount);
        transferAdjusment.setTargetTransactionType(TransactionTypeEnum.CREDIT);
        transferAdjusment.setSourceAmount(residualValue);
        transferAdjusment.setSourceTransactionType(TransactionTypeEnum.CREDIT);
        return transferAdjusment;
    }

}
