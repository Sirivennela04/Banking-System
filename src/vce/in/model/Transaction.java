package vce.in.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String type;
    private double amount;
    private double balanceAfter;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return type + ": $" + amount + ", Balance: $" + balanceAfter;
    }
}
