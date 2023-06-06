package com.pyyne.challenge.bank.tests;

import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountSource;
import com.pyyne.challenge.bank.Bank1Adapter;
import com.pyyne.challenge.bank.Bank2Adapter;
import com.pyyne.challenge.bank.BankAccountAdapter;
import com.pyyne.challenge.bank.BankController;
import com.pyyne.challenge.bank.model.BankTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BankControllerTest {
    private BankController bankController;
    private List<BankAccountAdapter> bankAdapters;

    @BeforeEach
    public void setup() {
        // Initialize the bankAdapters list
        bankAdapters = new ArrayList<>();

        Bank1AccountSource bank1AccountSource = new Bank1AccountSource();
        BankAccountAdapter bank1Adapter = new Bank1Adapter(bank1AccountSource);
        bankAdapters.add(bank1Adapter);

        Bank2AccountSource bank2AccountSource = new Bank2AccountSource();
        BankAccountAdapter bank2Adapter = new Bank2Adapter(bank2AccountSource);
        bankAdapters.add(bank2Adapter);

        bankController = new BankController(bankAdapters);
    }

    @Test
    public void testPrintBalances() {
        // Perform the action
        bankController.printBalances();

        for (BankAccountAdapter bankAdapter : bankAdapters) {
            List<Long> accountIds = getAccountIds();
            for (Long accountId : accountIds) {
                Double balance = bankAdapter.getAccountBalance(accountId);
                Assertions.assertNotNull(balance);
            }
        }
    }

    private List<Long> getAccountIds() {
        List<Long> accountIds = new ArrayList<>();
        accountIds.add(123456L);
        accountIds.add(789012L);

        return accountIds;
    }

    @Test
    public void testPrintTransactions() {
        bankController.printTransactions();

        List<BankTransaction> transactions = bankController.getTransactions(null, null);
        Assertions.assertEquals(12, transactions.size());
        Assertions.assertEquals(100d, transactions.get(0).getAmount());
        Assertions.assertEquals(BankTransaction.TYPE_CREDIT, transactions.get(0).getType());
        Assertions.assertEquals("Check deposit", transactions.get(0).getText());
    }
}
