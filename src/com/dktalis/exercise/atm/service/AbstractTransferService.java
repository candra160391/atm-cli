package com.dktalis.exercise.atm.service;

import com.dktalis.exercise.atm.Enum.TransactionTypeEnum;
import com.dktalis.exercise.atm.dto.TransferAdjusment;
import com.dktalis.exercise.atm.exception.SessionLoginException;
import com.dktalis.exercise.atm.exception.TransactionException;
import com.dktalis.exercise.atm.model.Account;

import java.math.BigDecimal;

public abstract class AbstractTransferService {

    public abstract Account getSourceAccount();
    public abstract AccountService getAccountService();
    public abstract Account getTargetAccount(String username);
    public abstract void validateTransfer(Account targetUser, Account transferAmount) throws SessionLoginException, TransactionException;
    public abstract TransferAdjusment doBeforeTransfer(Account sourceAccount, Account targetUser, BigDecimal transferAmount) throws SessionLoginException, TransactionException;

    public void transfer(String targetUser, BigDecimal transferAmount) throws SessionLoginException, TransactionException {
        Account sourceAccount = getSourceAccount();
        Account targetAccount = getTargetAccount(targetUser);

        validateTransfer(sourceAccount, targetAccount);
        TransferAdjusment transferAdjusment = doBeforeTransfer(sourceAccount, targetAccount, transferAmount);
        doTransfer(sourceAccount, targetAccount, transferAdjusment);
        doAfterTransfer(transferAdjusment, sourceAccount, targetAccount);
    }

    public void doTransfer(Account sourceAccount, Account targetAccount, TransferAdjusment transferAdjusment){
        if(transferAdjusment.getTargetAmount().compareTo(BigDecimal.ZERO) > 0){
            BigDecimal targetAccountBalance = targetAccount.getBalance();
            targetAccount.setBalance(targetAccountBalance.add(transferAdjusment.getTargetAmount()));
            targetAccount = getAccountService().updateAccount(targetAccount);
            System.out.println("Transferred $" + transferAdjusment.getTargetAmount() + " to " + targetAccount.getUser().getName());
        }

        BigDecimal sourceAccountBalance = sourceAccount.getBalance();

        if(transferAdjusment.getSourceTransactionType().equals(TransactionTypeEnum.CREDIT)){
            sourceAccount.setBalance(sourceAccountBalance.add(transferAdjusment.getSourceAmount()));
        }

        else if(transferAdjusment.getSourceTransactionType().equals(TransactionTypeEnum.DEBET)){
            sourceAccount.setBalance(sourceAccountBalance.subtract(transferAdjusment.getSourceAmount()));
        }

        sourceAccount = getAccountService().updateAccount(sourceAccount);
        System.out.println("Your balance is $" + sourceAccount.getBalance());
    }

    private void doAfterTransfer(TransferAdjusment transferAdjusment, Account sourceAccount, Account targetAccount) {

        if(transferAdjusment.hasReceivable()){
            updateReceivableAccount(sourceAccount, targetAccount, transferAdjusment);
            if(transferAdjusment.getDebtAmount().compareTo(BigDecimal.ZERO) > 0){
                sourceAccount = updateDebtAccount(sourceAccount, targetAccount, transferAdjusment);
            }
        }

        else if(transferAdjusment.hasDebt()){
            sourceAccount = updateDebtAccount(sourceAccount, targetAccount, transferAdjusment);
        }

        if(sourceAccount.getDebtAccount() != null){
            System.out.println("Owed $" + sourceAccount.getDebtAccount().getBalance() + " to " + sourceAccount.getDebtAccount().getUser().getName());
        }

        if(sourceAccount.getReceivableAccount() != null){
            System.out.println("Owed $" + sourceAccount.getReceivableAccount().getBalance() + " from " + sourceAccount.getReceivableAccount().getUser().getName());
        }
    }

    public void updateReceivableAccount(Account sourceAccount, Account targetAccount, TransferAdjusment transferAdjusment){
        getAccountService().upsertReceivableAccount(sourceAccount, targetAccount, transferAdjusment.getReceivableAmount(), transferAdjusment.getDebtTransactionType());
        getAccountService().upsertDebtAccount(targetAccount, sourceAccount, transferAdjusment.getReceivableAmount(), transferAdjusment.getDebtTransactionType());
    }

    public Account updateDebtAccount(Account sourceAccount, Account targetAccount, TransferAdjusment transferAdjusment){
        getAccountService().upsertReceivableAccount(targetAccount, sourceAccount, transferAdjusment.getDebtAmount(), TransactionTypeEnum.CREDIT);
        sourceAccount = getAccountService().upsertDebtAccount(sourceAccount, targetAccount, transferAdjusment.getDebtAmount(), TransactionTypeEnum.CREDIT);

        return sourceAccount;
    }
}
