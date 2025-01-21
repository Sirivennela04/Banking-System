package vce.in.model;

public class CurrentAccount extends Account {

    public CurrentAccount() {
        super();
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            logTransaction("Withdrawal", amount);
            return true;
        } else {
            return false; // Insufficient balance
        }
    }
}
