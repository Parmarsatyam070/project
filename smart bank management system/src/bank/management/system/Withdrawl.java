package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Withdrawl extends JFrame implements ActionListener {
    JTextField amountField;
    JButton withdrawButton, backButton;
    String pinNumber;

    Withdrawl(String pinNumber) {
        this.pinNumber = pinNumber;

        setTitle("Withdraw Money");
        setLayout(null);

        JLabel heading = new JLabel("Withdraw Funds");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(200, 40, 300, 30);
        add(heading);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        amountLabel.setBounds(100, 120, 150, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setBounds(250, 120, 200, 30);
        add(amountField);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(250, 180, 100, 30);
        withdrawButton.setBackground(Color.BLACK);
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        backButton = new JButton("Back");
        backButton.setBounds(360, 180, 90, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        getContentPane().setBackground(Color.WHITE);
        setSize(600, 300);
        setLocation(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == withdrawButton) {
            String amount = amountField.getText().trim();

            if (amount.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter an amount.");
                return;
            }

            try {
                int withdrawAmount = Integer.parseInt(amount);
                if (withdrawAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "Enter a valid positive amount.");
                    return;
                }

                Conn conn = new Conn();

                // Step 1: Check balance
                PreparedStatement ps = conn.connection.prepareStatement(
                        "SELECT type, amount FROM banks WHERE pin = ?");
                ps.setString(1, pinNumber);
                ResultSet rs = ps.executeQuery();

                int balance = 0;
                while (rs.next()) {
                    String type = rs.getString("type");
                    int amt = rs.getInt("amount");
                    if (type.equalsIgnoreCase("Deposit")) {
                        balance += amt;
                    } else if (type.equalsIgnoreCase("Withdraw")) {
                        balance -= amt;
                    }
                }

                if (withdrawAmount > balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance.");
                } else {
                    // Step 2: Insert withdrawal transaction
                    PreparedStatement pst = conn.connection.prepareStatement(
                            "INSERT INTO banks(pin, date, type, amount) VALUES (?, NOW(), ?, ?)");

                    pst.setString(1, pinNumber);
                    pst.setString(2, "Withdraw");
                    pst.setInt(3, withdrawAmount);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "₹" + withdrawAmount + " withdrawn successfully.");
                    setVisible(false);
                    new Transactions(pinNumber).setVisible(true);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter a numeric amount.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Transaction Failed: " + e.getMessage());
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Transactions(pinNumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Withdrawl("1234");  // Replace with actual PIN for testing
    }
}
