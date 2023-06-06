package com.pyyne.challenge.bank;

/**
 * Controller that pulls information form multiple bank integrations and prints them to the console.
 *
 * Created by Par Renyard on 5/12/21.
 */

import com.pyyne.challenge.bank.model.BankTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BankController {
    private List<BankAccountAdapter> bankAdapters;

    public BankController(List<BankAccountAdapter> bankAdapters) {
        this.bankAdapters = bankAdapters;
    }

    public void printBalances() {
        for (BankAccountAdapter adapter : bankAdapters) {
            long accountId = 123456789;
            Double balance = adapter.getAccountBalance(accountId);
            String currency = adapter.getAccountCurrency(accountId);
            System.out.println("Balance: " + balance + " " + currency);
        }
    }

    public void printTransactions() {
        long accountId = 123456789;
        Date fromDate = getFromDate();
        Date toDate = getToDate();

        for (BankAccountAdapter adapter : bankAdapters) {
            List<BankTransaction> transactions = adapter.getTransactions(accountId, fromDate, toDate);
            System.out.println("Transactions:");
            for (BankTransaction transaction : transactions) {
                System.out.println(transaction.getAmount() + " "
                        + (transaction.getType() == BankTransaction.TYPE_CREDIT ? "Credit" : "Debit")
                        + " " + transaction.getText());
            }
        }
    }

    public List<BankTransaction> getTransactions(Date fromDate, Date toDate) {
        List<BankTransaction> allTransactions = new ArrayList<>();


        // Iterate over each bank adapter
        for (BankAccountAdapter bankAdapter : bankAdapters) {
            List<Long> accountIds = getAccountIds();

            // Iterate over each account ID
            for (Long accountId : accountIds) {
                // Retrieve the transactions from the bank adapter
                List<BankTransaction> transactions = bankAdapter.getTransactions(accountId, fromDate, toDate);

                // Add the transactions to the main list
                allTransactions.addAll(transactions);
            }
        }

        return allTransactions;
    }

    private List<Long> getAccountIds() {
        List<Long> accountIds = new ArrayList<>();


        accountIds.add(123456L);
        accountIds.add(789012L);

        return accountIds;
    }

    private Date getFromDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    private Date getToDate() {
        return new Date();
    }
}
