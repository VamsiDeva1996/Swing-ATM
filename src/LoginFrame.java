import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    JTextField cardField;
    JPasswordField pinField;
    JButton loginBtn;

    public LoginFrame() {
        setTitle("ATM Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Card Number:"));
        cardField = new JTextField();
        add(cardField);

        add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        add(pinField);

        loginBtn = new JButton("Login");
        add(loginBtn);

        loginBtn.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String card = cardField.getText();
        String pin = new String(pinField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE card_number=? AND pin=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, card);
            ps.setString(2, pin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                new MainMenuFrame(id);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Card or PIN!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

