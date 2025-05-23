package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Deposit extends JFrame implements ActionListener {

        JTextField amountField;
        JButton depositBtn, backBtn;
        String pin;

        public Deposit(String pin) {
            this.pin = pin;

            setTitle("Deposit");
            setLayout(null);

            JLabel heading = new JLabel("Deposit Money");
            heading.setFont(new Font("Arial", Font.BOLD, 22));
            heading.setForeground(Color.WHITE);
            heading.setBounds(160, 30, 200, 30);
            add(heading);

            JLabel label = new JLabel("Enter Amount:");
            label.setForeground(Color.WHITE);
            label.setBounds(80, 100, 150, 30);
            add(label);

            amountField = new JTextField();
            amountField.setBounds(200, 100, 180, 30);
            add(amountField);

            depositBtn = new JButton("Deposit");
            depositBtn.setBounds(100, 160, 120, 30);
            depositBtn.addActionListener(this);
            add(depositBtn);

            backBtn = new JButton("Back");
            backBtn.setBounds(240, 160, 120, 30);
            backBtn.addActionListener(this);
            add(backBtn);

            getContentPane().setBackground(new Color(30, 30, 40));
            setSize(450, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == depositBtn) {
                String amountStr = amountField.getText().trim();
                if (amountStr.equals("")) {
                    JOptionPane.showMessageDialog(this, "Please enter an amount.");
                    return;
                }

                try {
                    double amount = Double.parseDouble(amountStr);
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartbanksystem", "root", "Parmarsatyam09@#");

                    String query = "INSERT INTO banks(pin, date , type, amount) VALUES (?, NOW() , ? , ? )";
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setString(1, pin);
                    pst.setString(2, "Deposit");
                    pst.setDouble(3, amount);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Rs. " + amount + " deposited successfully.");
                    setVisible(false);
                    new main_class(pin).setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
                }

            } else if (ae.getSource() == backBtn) {
                setVisible(false);
                new main_class(pin).setVisible(true);
            }
        }

        public static void main(String[] args) {
            new Deposit("1234");
        }
    }