package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JTextField cardField;
    JPasswordField pinField;
    JButton loginBtn, clearBtn, signupBtn;

    Login() {
        setTitle("Bank Login");
        setLayout(null);

        JLabel title = new JLabel("Welcome to MyBank");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(100, 40, 300, 30);
        add(title);

        JLabel cardLabel = new JLabel("Card Number");
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBounds(100, 100, 200, 30);
        add(cardLabel);

        cardField = new JTextField();
        cardField.setBounds(100, 130, 250, 30);
        add(cardField);

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setForeground(Color.WHITE);
        pinLabel.setBounds(100, 170, 200, 30);
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(100, 200, 250, 30);
        add(pinField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 250, 100, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        clearBtn = new JButton("Clear");
        clearBtn.setBounds(220, 250, 100, 30);
        clearBtn.addActionListener(this);
        add(clearBtn);

        signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(160, 300, 100, 30);
        signupBtn.addActionListener(this);
        add(signupBtn);

        getContentPane().setBackground(new Color(30, 30, 40));
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearBtn) {
            cardField.setText("");
            pinField.setText("");
        } else if (e.getSource() == loginBtn) {
            String cardNumber = cardField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            if (cardNumber.equals("") || pin.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter both card number and PIN.");
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/smartbanksystem", "root", "Parmarsatyam09@#");
                //String query = "select * from loginss where card_number = '" + cardNumber + "' and  pin = '" + pin + "'";
                String query = "SELECT * FROM loginss WHERE card_number = ? AND pin = ?";
                System.out.println("Card: " + cardNumber + " | PIN: " + pin);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, cardNumber);
                pst.setString(2, pin);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid card number or PIN.");
                }

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        } else if (e.getSource() == signupBtn) {
            setVisible(false);
            new Signup1().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
