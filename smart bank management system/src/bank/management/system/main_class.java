package bank.management.system;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class main_class extends JFrame implements ActionListener {



        JButton depositBtn, withdrawBtn, checkBalanceBtn, logoutBtn;
        String pin;

        public main_class(String pin) {
            this.pin = pin;

            setTitle("Banking Dashboard");
            setLayout(null);

            JLabel heading = new JLabel("Welcome to Online Banking");
            heading.setFont(new Font("Arial", Font.BOLD, 22));
            heading.setForeground(Color.WHITE);
            heading.setBounds(100, 30, 300, 30);
            add(heading);

            depositBtn = new JButton("Deposit");
            depositBtn.setBounds(150, 100, 150, 30);
            depositBtn.addActionListener(this);
            add(depositBtn);

            withdrawBtn = new JButton("Withdraw");
            withdrawBtn.setBounds(150, 150, 150, 30);
            withdrawBtn.addActionListener(this);
            add(withdrawBtn);

            checkBalanceBtn = new JButton("Check Balance");
            checkBalanceBtn.setBounds(150, 200, 150, 30);
            checkBalanceBtn.addActionListener(this);
            add(checkBalanceBtn);

            logoutBtn = new JButton("Logout");
            logoutBtn.setBounds(150, 250, 150, 30);
            logoutBtn.addActionListener(this);
            add(logoutBtn);

            getContentPane().setBackground(new Color(25, 25, 40));
            setSize(450, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == depositBtn) {
                setVisible(false);
                new Deposit(pin).setVisible(true);
            } else if (e.getSource() == withdrawBtn) {
                setVisible(false);
                new Withdrawl(pin).setVisible(true);
            } else if (e.getSource() == checkBalanceBtn) {
                JOptionPane.showMessageDialog(this, "Feature coming soon...");
            } else if (e.getSource() == logoutBtn) {
                setVisible(false);
                new Login().setVisible(true);
            }
        }

        public static void main(String[] args) {
            new main_class("1234");
        }
    }