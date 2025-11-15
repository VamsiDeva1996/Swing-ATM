import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MainMenuFrame extends JFrame {
    int accountId;

    public MainMenuFrame(int accountId) {
        this.accountId = accountId;

        setTitle("ATM Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JButton withdrawBtn = new JButton("Withdraw");
        JButton depositBtn = new JButton("Deposit");
        JButton balanceBtn = new JButton("Check Balance");
        JButton exitBtn = new JButton("Exit");

        add(withdrawBtn);
        add(depositBtn);
        add(balanceBtn);
        add(exitBtn);

        withdrawBtn.addActionListener(e -> withdraw());
        depositBtn.addActionListener(e -> deposit());
        balanceBtn.addActionListener(e -> checkBalance());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void withdraw() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        double amount = Double.parseDouble(amountStr);

        try (Connection conn = DBConnection.getConnection()) {
            String checkSql = "SELECT balance FROM accounts WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(checkSql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (amount <= balance) {
                    String updateSql = "UPDATE accounts SET balance = balance - ? WHERE id=?";
                    PreparedStatement ups = conn.prepareStatement(updateSql);
                    ups.setDouble(1, amount);
                    ups.setInt(2, accountId);
                    ups.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Withdraw Successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deposit() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        double amount = Double.parseDouble(amountStr);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deposit Successful!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkBalance() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT balance FROM accounts WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Balance: ₹" + rs.getDouble("balance"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

