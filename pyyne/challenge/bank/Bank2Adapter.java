package com.pyyne.challenge.bank;

import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;
import com.pyyne.challenge.bank.model.BankTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank2Adapter implements BankAccountAdapter {
    private Bank2AccountSource bank2AccountSource;

    public Bank2Adapter(Bank2AccountSource bank2AccountSource) {
        this.bank2AccountSource = bank2AccountSource;
    }

    @Override
    public Double getAccountBalance(long accountId) {
        Bank2AccountBalance accountBalance = bank2AccountSource.getBalance(accountId);
        return accountBalance.getBalance();
    }

    @Override
    public String getAccountCurrency(long accountId) {
        Bank2AccountBalance accountBalance = bank2AccountSource.getBalance(accountId);
        return accountBalance.getCurrency();
    }

    @Override
    public List<BankTransaction> getTransactions(long accountId, Date fromDate, Date toDate) {
        List<Bank2AccountTransaction> bank2Transactions = bank2AccountSource.getTransactions(accountId, fromDate, toDate);
        List<BankTransaction> adaptedTransactions = new ArrayList<>();

        for (Bank2AccountTransaction bank2Transaction : bank2Transactions) {
            BankTransaction adaptedTransaction = adaptBank2Transaction(bank2Transaction);
            adaptedTransactions.add(adaptedTransaction);
        }

        return adaptedTransactions;
    }

    private BankTransaction adaptBank2Transaction(Bank2AccountTransaction bank2Transaction) {
        double amount = bank2Transaction.getAmount();
        int type = convertTransactionType(bank2Transaction.getType());
        String text = bank2Transaction.getText();

        // Perform any necessary adaptation/transformation based on Bank2AccountTransaction to BankTransaction

        return new BankTransaction(amount, type, text);
    }

    private int convertTransactionType(Bank2AccountTransaction.TRANSACTION_TYPES type) {
        if (type == Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT) {
            return BankTransaction.TYPE_DEBIT;
        } else if (type == Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT) {
            return BankTransaction.TYPE_CREDIT;
        } else {
            // Handle any other types as needed
            return -1;
        }
    }
}
