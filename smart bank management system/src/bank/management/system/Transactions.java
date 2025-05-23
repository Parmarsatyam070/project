package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Transactions extends JFrame implements ActionListener {
    JButton depositButton, withdrawButton, fastCashButton, miniStatementButton;
    JButton pinChangeButton, balanceEnquiryButton, exitButton;
    String pinNumber;

    Transactions(String pinNumber) {
        this.pinNumber = pinNumber;

        setTitle("Transaction Menu");
        setLayout(null);

        JLabel heading = new JLabel("Please Select Your Transaction");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(120, 30, 400, 30);
        add(heading);

        depositButton = createButton("Deposit", 100, 100);
        withdrawButton = createButton("Withdraw", 300, 100);
        fastCashButton = createButton("Fast Cash", 100, 160);
        miniStatementButton = createButton("Mini Statement", 300, 160);
        pinChangeButton = createButton("PIN Change", 100, 220);
        balanceEnquiryButton = createButton("Balance Enquiry", 300, 220);
        exitButton = createButton("Exit", 200, 280);

        getContentPane().setBackground(Color.WHITE);
        setSize(550, 400);
        setLocation(300, 200);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 140, 40);
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.addActionListener(this);
        add(btn);
        return btn;
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();

        if (src == depositButton) {
            setVisible(false);
            new Deposit(pinNumber);
        } else if (src == withdrawButton) {
            setVisible(false);
            new Withdrawl(pinNumber);
        } else if (src == fastCashButton) {
            setVisible(false);
            new FastCash(pinNumber);
        } else if (src == miniStatementButton) {
            new MiniStatement(pinNumber);
        } else if (src == pinChangeButton) {
            setVisible(false);
            new PinChange(pinNumber);
        } else if (src == balanceEnquiryButton) {
            setVisible(false);
            new BalanceEnquiry(pinNumber);
        } else if (src == exitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Transactions("");
    }
}