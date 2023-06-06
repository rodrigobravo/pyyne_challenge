package com.pyyne.challenge.bank.model;
public class BankTransaction {
    public static final int TYPE_CREDIT = 1;
    public static final int TYPE_DEBIT = 2;

    private double amount;
    private int type;
    private String text;

    public BankTransaction(double amount, int type, String text) {
        this.amount = amount;
        this.type = type;
        this.text = text;
    }

    public double getAmount() {
        return amount;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
