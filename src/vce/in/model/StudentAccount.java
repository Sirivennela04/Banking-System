package vce.in.model;

public class StudentAccount extends Account {

    public StudentAccount() {
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
