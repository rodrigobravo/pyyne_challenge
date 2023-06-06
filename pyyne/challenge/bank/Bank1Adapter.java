package com.pyyne.challenge.bank;

import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.pyyne.challenge.bank.model.BankTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank1Adapter implements BankAccountAdapter {
    private Bank1AccountSource bank1AccountSource;

    public Bank1Adapter(Bank1AccountSource bank1AccountSource) {
        this.bank1AccountSource = bank1AccountSource;
    }

    @Override
    public Double getAccountBalance(long accountId) {
        return bank1AccountSource.getAccountBalance(accountId);
    }

    @Override
    public String getAccountCurrency(long accountId) {
        return bank1AccountSource.getAccountCurrency(accountId);
    }

    @Override
    public List<BankTransaction> getTransactions(long accountId, Date fromDate, Date toDate) {
        List<Bank1Transaction> bank1Transactions = bank1AccountSource.getTransactions(accountId, fromDate, toDate);
        List<BankTransaction> adaptedTransactions = new ArrayList<>();

        for (Bank1Transaction bank1Transaction : bank1Transactions) {
            BankTransaction adaptedTransaction = adaptBank1Transaction(bank1Transaction);
            adaptedTransactions.add(adaptedTransaction);
        }

        return adaptedTransactions;
    }

    private BankTransaction adaptBank1Transaction(Bank1Transaction bank1Transaction) {
        double amount = bank1Transaction.getAmount();
        int type = bank1Transaction.getType();
        String text = bank1Transaction.getText();

        // Perform any necessary adaptation/transformation based on Bank1Transaction to BankTransaction

        return new BankTransaction(amount, type, text);
    }
}
