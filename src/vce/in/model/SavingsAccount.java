package vce.in.model;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        super();
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
            logTransaction("Withdrawal", amount);
            return true;
        } else {
            return false; // Insufficient balance
        }
    }
}
