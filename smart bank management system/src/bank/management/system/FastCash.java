package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FastCash extends JFrame implements ActionListener {
    JButton[] amountButtons;
    JButton backButton;
    String pinNumber;
    String[] amounts = {"₹100", "₹500", "₹1000", "₹2000", "₹5000", "₹10000"};

    FastCash(String pinNumber) {
        this.pinNumber = pinNumber;

        setTitle("Fast Cash");
        setLayout(null);

        JLabel heading = new JLabel("Select Withdrawal Amount");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(140, 30, 400, 30);
        add(heading);

        amountButtons = new JButton[6];
        int x = 100, y = 100;

        for (int i = 0; i < amounts.length; i++) {
            amountButtons[i] = new JButton(amounts[i]);
            amountButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            amountButtons[i].setBounds(x, y, 150, 40);
            amountButtons[i].setBackground(new Color(0, 128, 255));
            amountButtons[i].setForeground(Color.WHITE);
            amountButtons[i].addActionListener(this);
            add(amountButtons[i]);

            if (i % 2 == 1) {
                x = 100;
                y += 60;
            } else {
                x = 300;
            }
        }

        backButton = new JButton("Back");
        backButton.setBounds(200, y + 60, 150, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        getContentPane().setBackground(Color.WHITE);
        setSize(600, 450);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String amountStr = ae.getActionCommand();

        if (amountStr.equals("Back")) {
            setVisible(false);
            new Transactions(pinNumber);
            return;
        }

        // Remove ₹ and parse amount as double
        String numericAmount = amountStr.substring(1);
        double withdrawAmount = Double.parseDouble(numericAmount);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM banks WHERE pin = '" + pinNumber + "'");
            double balance = 0;

            while (rs.next()) {
                String type = rs.getString("type");
                double amt = Double.parseDouble(rs.getString("amount"));
                if (type.equalsIgnoreCase("Deposit")) {
                    balance += amt;
                } else {
                    balance -= amt;
                }
            }

            if (withdrawAmount > balance) {
                JOptionPane.showMessageDialog(null, "Insufficient balance.");
            } else {
                PreparedStatement pst = conn.connection.prepareStatement(
                        "INSERT INTO banks(pin, date, type, amount) VALUES (?, NOW(), ?, ?)");

                pst.setString(1, pinNumber);
                pst.setString(2, "Withdraw");
                pst.setDouble(3, withdrawAmount);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "₹" + withdrawAmount + " withdrawn successfully.");
                setVisible(false);
                new Transactions(pinNumber);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Transaction Failed: " + e.getMessage());
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new FastCash("");
    }
}
