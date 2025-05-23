package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BalanceEnquiry extends JFrame implements ActionListener {
    JButton backButton;
    JLabel balanceLabel;
    String pinNumber;

    BalanceEnquiry(String pinNumber) {
        this.pinNumber = pinNumber;

        setTitle("Balance Enquiry");
        setLayout(null);

        JLabel heading = new JLabel("Your Current Balance");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(160, 40, 300, 30);
        add(heading);

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        balanceLabel.setBounds(180, 100, 300, 30);
        add(balanceLabel);

        backButton = new JButton("Back");
        backButton.setBounds(230, 170, 120, 35);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);
        add(backButton);

        getContentPane().setBackground(Color.WHITE);
        setSize(550, 300);
        setLocation(300, 200);
        setVisible(true);

        showBalance();
    }

    private void showBalance() {
        double balance = 0;
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM banks WHERE pin = '" + pinNumber + "'");

            while (rs.next()) {
                String type = rs.getString("type");
                double amt = Double.parseDouble(rs.getString("amount")); // FIXED: use double
                if (type.equalsIgnoreCase("Deposit")) {
                    balance += amt;
                } else if (type.equalsIgnoreCase("Withdraw") || type.equalsIgnoreCase("Withdrawal")) {
                    balance -= amt;
                }
            }

            balanceLabel.setText("Available Balance: ₹" + String.format("%.2f", balance));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error retrieving balance.");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pinNumber);
    }

    public static void main(String[] args) {
        new BalanceEnquiry(""); // You can pass a test PIN here
    }
}
