package vce.in.model;

import java.util.ArrayList;

public abstract class Account {
    protected double balance;
    protected ArrayList<Transaction> transactionHistory; // Changed to ArrayList

    public Account() {
        transactionHistory = new ArrayList<>();
    }

    public abstract boolean withdraw(double amount);
    
    public boolean deposit(double amount) {
        balance += amount;
        logTransaction("Deposit", amount);
        return true;
    }

    protected void logTransaction(String type, double amount) {
        transactionHistory.add(new Transaction(type, amount, balance)); // Directly added to the list
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    
    public double getBalance() {
        return balance;
    }
}