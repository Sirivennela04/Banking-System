package vce.in.view;

import vce.in.model.*;
import vce.in.launch.IOHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BankingUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Account account;
    private IOHandler ioHandler;

    private JPanel accountSelectionPanel;
    private JButton savingsButton, currentButton, studentButton;

    private JPanel bankingOperationsPanel;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JButton depositButton, withdrawButton;

    public BankingUI() {
        ioHandler = new IOHandler();
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        initializeAccountSelectionPanel();
        initializeBankingOperationsPanel();

        mainPanel.add(accountSelectionPanel, "AccountSelection");
        mainPanel.add(bankingOperationsPanel, "BankingOperations");

        add(mainPanel);
        setTitle("Banking System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeAccountSelectionPanel() {
        accountSelectionPanel = new JPanel();
        savingsButton = new JButton("Savings Account");
        currentButton = new JButton("Current Account");
        studentButton = new JButton("Student Account");

        savingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAccountType("Savings");
            }
        });
        currentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAccountType("Current");
            }
        });
        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAccountType("Student");
            }
        });

        accountSelectionPanel.add(savingsButton);
        accountSelectionPanel.add(currentButton);
        accountSelectionPanel.add(studentButton);
    }

    private void initializeBankingOperationsPanel() {
        bankingOperationsPanel = new JPanel();
        bankingOperationsPanel.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveTransactionItem = new JMenuItem("Save Transaction");
        JMenuItem viewHistoryItem = new JMenuItem("View History");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(saveTransactionItem);
        fileMenu.add(viewHistoryItem);
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem creditItem = new JMenuItem("Credits");
        JMenuItem aboutItem = new JMenuItem("About");

        helpMenu.add(creditItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        bankingOperationsPanel.add(menuBar, BorderLayout.NORTH);

        // Input fields and buttons
        amountField = new JTextField(15);
        balanceLabel = new JLabel("Balance: $0.00"); // Set default value
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                depositAmount();
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdrawAmount();
            }
        });

        JPanel operationPanel = new JPanel();
        operationPanel.add(amountField);
        operationPanel.add(depositButton);
        operationPanel.add(withdrawButton);
        operationPanel.add(balanceLabel);

        bankingOperationsPanel.add(operationPanel, BorderLayout.CENTER);

        // Action listeners for menu items
        saveTransactionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveTransaction();
            }
        });
        viewHistoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewTransactionHistory();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        creditItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCredits();
            }
        });
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
    }

    private void selectAccountType(String accountType) {
        switch (accountType) {
            case "Savings":
                account = new SavingsAccount();
                break;
                case "Current":
                account = new CurrentAccount();
                break;
            case "Student":
                account = new StudentAccount();
                break;
        }
        updateBalanceLabel();
        cardLayout.show(mainPanel, "BankingOperations");
    }

    private void updateBalanceLabel() {
        if (account != null) {
            balanceLabel.setText("Balance: $" + account.getBalance());
        }
    }

    private void depositAmount() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.deposit(amount)) {
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "Deposit Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdrawAmount() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "Withdrawal Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient funds or minimum balance required.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTransaction() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ioHandler.setDataFileName("data.bin");
        if (ioHandler.readState()) {
            transactions = ioHandler.getTransactionHistory();
        }
        transactions.addAll(account.getTransactionHistory());
        ioHandler.writeState(transactions);
    }

    private void viewTransactionHistory() {
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        ioHandler.setDataFileName("data.bin");
        ioHandler.readState();
        allTransactions = ioHandler.getTransactionHistory();

        StringBuilder history = new StringBuilder();
        for (Transaction t : allTransactions) {
            history.append(t.toString()).append("\n");
        }
        if (history.length() == 0) {
            JOptionPane.showMessageDialog(this, "No transaction history available.", "History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, history.toString(), "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showCredits() {
        String credits = "Developed by:\nCh. Sirivennela\nN. Amulya\nRoll No: \n1602-23-737-116\n1602-23-737-072";
        JOptionPane.showMessageDialog(this, credits, "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        String about = "Banking System Application\n\nThis application allows users to perform deposit and withdrawal operations.\nIt supports multiple account types and tracks transaction history.";
        JOptionPane.showMessageDialog(this, about, "About", JOptionPane.INFORMATION_MESSAGE);
    }
}