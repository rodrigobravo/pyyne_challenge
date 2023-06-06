package com.pyyne.challenge.bank;

import com.pyyne.challenge.bank.model.BankTransaction;

import java.util.Date;
import java.util.List;

public interface BankAccountAdapter {
    Double getAccountBalance(long accountId);
    String getAccountCurrency(long accountId);
    List<BankTransaction> getTransactions(long accountId, Date fromDate, Date toDate);
}
