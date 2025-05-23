package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {
    JPasswordField newPinField, reenterPinField;
    JButton changeButton, backButton;
    String pinNumber;

    PinChange(String pinNumber) {
        this.pinNumber = pinNumber;

        setTitle("Change PIN");
        setLayout(null);

        JLabel heading = new JLabel("Change Your PIN");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(180, 40, 300, 30);
        add(heading);

        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        newPinLabel.setBounds(100, 120, 150, 30);
        add(newPinLabel);

        newPinField = new JPasswordField();
        newPinField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPinField.setBounds(250, 120, 200, 30);
        add(newPinField);

        JLabel reenterPinLabel = new JLabel("Re-Enter New PIN:");
        reenterPinLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        reenterPinLabel.setBounds(100, 170, 180, 30);
        add(reenterPinLabel);

        reenterPinField = new JPasswordField();
        reenterPinField.setFont(new Font("Arial", Font.PLAIN, 16));
        reenterPinField.setBounds(250, 170, 200, 30);
        add(reenterPinField);

        changeButton = new JButton("Change PIN");
        changeButton.setBounds(250, 230, 120, 35);
        changeButton.setBackground(new Color(0, 128, 0));
        changeButton.setForeground(Color.WHITE);
        changeButton.addActionListener(this);
        add(changeButton);

        backButton = new JButton("Back");
        backButton.setBounds(380, 230, 70, 35);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        getContentPane().setBackground(Color.WHITE);
        setSize(600, 350);
        setLocation(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == changeButton) {
            String newPin = String.valueOf(newPinField.getPassword());
            String reenterPin = String.valueOf(reenterPinField.getPassword());

            if (newPin.equals("") || reenterPin.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter and confirm your new PIN.");
                return;
            }

            if (!newPin.equals(reenterPin)) {
                JOptionPane.showMessageDialog(null, "Entered PINs do not match.");
                return;
            }

            try {
                Conn conn = new Conn();
                String updateBank = "UPDATE banks SET pin = '" + newPin + "' WHERE pin = '" + pinNumber + "'";
                String updateLogin = "UPDATE login SET pinNumber = '" + newPin + "' WHERE pinNumber = '" + pinNumber + "'";
                String updateSignup3 = "UPDATE signup3 SET pin = '" + newPin + "' WHERE pin = '" + pinNumber + "'";

                conn.statement.executeUpdate(updateBank);
                conn.statement.executeUpdate(updateLogin);
                conn.statement.executeUpdate(updateSignup3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully.");
                setVisible(false);
                new Transactions(newPin);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error changing PIN.");
                e.printStackTrace();
            }

        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Transactions(pinNumber);
        }
    }

    public static void main(String[] args) {
        new PinChange("");
    }
}