package vce.in.launch;
import vce.in.view.*;
import javax.swing.SwingUtilities;

public class BankingLaunch {
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankingUI();
            }
        });
    }
}
