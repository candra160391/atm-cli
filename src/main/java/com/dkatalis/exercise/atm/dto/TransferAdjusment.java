package com.dkatalis.exercise.atm.dto;

import com.dkatalis.exercise.atm.Enum.TransactionTypeEnum;

import java.math.BigDecimal;

public class TransferAdjusment {
    private boolean hasDebt;
    private boolean hasReceivable;
    private BigDecimal debtAmount;
    private BigDecimal receivableAmount;
    private TransactionTypeEnum receiveTransactionType;
    private TransactionTypeEnum debtTransactionType;
    private BigDecimal targetAmount;
    private TransactionTypeEnum targetTransactionType;
    private BigDecimal sourceAmount;
    private TransactionTypeEnum sourceTransactionType;

    public boolean hasDebt() {
        return hasDebt;
    }

    public void setHasDebt(boolean hasDebt) {
        this.hasDebt = hasDebt;
    }

    public boolean hasReceivable() {
        return hasReceivable;
    }

    public void setHasReceivable(boolean hasReceivable) {
        this.hasReceivable = hasReceivable;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(BigDecimal debtAmount) {
        this.debtAmount = debtAmount;
    }

    public BigDecimal getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(BigDecimal receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public TransactionTypeEnum getTargetTransactionType() {
        return targetTransactionType;
    }

    public void setTargetTransactionType(TransactionTypeEnum targetTransactionType) {
        this.targetTransactionType = targetTransactionType;
    }

    public TransactionTypeEnum getSourceTransactionType() {
        return sourceTransactionType;
    }

    public void setSourceTransactionType(TransactionTypeEnum sourceTransactionType) {
        this.sourceTransactionType = sourceTransactionType;
    }

    public TransactionTypeEnum getDebtTransactionType() {
        return debtTransactionType;
    }

    public void setDebtTransactionType(TransactionTypeEnum debtTransactionType) {
        this.debtTransactionType = debtTransactionType;
    }

    public TransactionTypeEnum getReceiveTransactionType() {
        return receiveTransactionType;
    }

    public void setReceiveTransactionType(TransactionTypeEnum receiveTransactionType) {
        this.receiveTransactionType = receiveTransactionType;
    }
}
