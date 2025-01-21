package vce.in.launch;

import vce.in.model.Transaction;
import java.io.*;
import java.util.ArrayList;

public class IOHandler {
    private ArrayList<Transaction> transactionHistory;
    private File dataFile;

    public void setDataFileName(String fileName) {
        this.dataFile = new File(fileName);
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean writeState(ArrayList<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
        boolean isSaved = false;
        try (FileOutputStream fOut = new FileOutputStream(dataFile);
             ObjectOutputStream oOut = new ObjectOutputStream(fOut)) {
            oOut.writeObject(transactionHistory);
            isSaved = true;
            System.out.println("Transaction history saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    public boolean readState() {
        boolean isRetrieved = false;
        try {
            if (!dataFile.exists()) {
                System.out.println("File not found, creating a new one.");
                transactionHistory = new ArrayList<>();
                return true;
            }
            try (FileInputStream fIn = new FileInputStream(dataFile);
                 ObjectInputStream oIn = new ObjectInputStream(fIn)) {
                this.transactionHistory = (ArrayList<Transaction>) oIn.readObject();
                isRetrieved = true;
                System.out.println("Transaction history loaded successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading the file or file not found.");
            e.printStackTrace();
        }
        return isRetrieved;
    }
}