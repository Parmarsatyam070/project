package bank.management.system;


import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;
import java.rmi.server.ExportException;
import java.awt.event.*;
import java.util.*;

public class Signup3 extends JFrame implements ActionListener {

    JRadioButton saving, current, fd, rd;
    JCheckBox atmCard, internetBanking, mobileBanking, alerts, chequeBook, eStatement, declaration;
    JButton submitBtn;
    String formNo;
    String accountType, cardNumber, pin, services = "";

    public Signup3(String formNo) {
        this.formNo = formNo;

        setTitle("Sign Up - Page 3");
        setLayout(null);

        JLabel title = new JLabel("Account Details");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(150, 30, 250, 30);
        add(title);

        addLabel("Account Type:", 80);
        saving = new JRadioButton("Saving Account");
        current = new JRadioButton("Current Account");
        fd = new JRadioButton("Fixed Deposit Account");
        rd = new JRadioButton("Recurring Deposit Account");
        saving.setBounds(200, 80, 200, 25);
        current.setBounds(200, 110, 200, 25);
        fd.setBounds(200, 140, 250, 25);
        rd.setBounds(200, 170, 250, 25);
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(saving);
        typeGroup.add(current);
        typeGroup.add(fd);
        typeGroup.add(rd);
        add(saving);
        add(current);
        add(fd);
        add(rd);

        addLabel("Services Required:", 210);
        atmCard = new JCheckBox("ATM Card");
        internetBanking = new JCheckBox("Internet Banking");
        mobileBanking = new JCheckBox("Mobile Banking");
        alerts = new JCheckBox("Email & SMS Alerts");
        chequeBook = new JCheckBox("Cheque Book");
        eStatement = new JCheckBox("E-Statement");
        declaration = new JCheckBox("I hereby declare that the above details are correct.");

        int y = 240;
        for (JCheckBox box : new JCheckBox[]{atmCard, internetBanking, mobileBanking, alerts, chequeBook, eStatement}) {
            box.setForeground(Color.WHITE);
            box.setBounds(200, y, 250, 25);
            add(box);
            y += 30;
        }

        declaration.setForeground(Color.WHITE);
        declaration.setBounds(50, y + 10, 400, 25);
        add(declaration);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(200, y + 50, 100, 30);
        submitBtn.addActionListener(this);
        add(submitBtn);

        getContentPane().setBackground(new Color(30, 30, 40));
        setSize(500, y + 140);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBounds(50, y, 150, 25);
        add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!declaration.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please agree to the declaration.");
            return;
        }

        if (saving.isSelected()) accountType = "Saving";
        else if (current.isSelected()) accountType = "Current";
        else if (fd.isSelected()) accountType = "Fixed Deposit";
        else if (rd.isSelected()) accountType = "Recurring Deposit";
        else {
            JOptionPane.showMessageDialog(this, "Select an account type.");
            return;
        }

        Random rand = new Random();
        cardNumber = "" + (Math.abs(rand.nextLong()) % 90000000L + 5040936000000000L);
        pin = "" + (Math.abs(rand.nextInt() % 9000) + 1000);

        if (atmCard.isSelected()) services += " ATM Card";
        if (internetBanking.isSelected()) services += " Internet Banking";
        if (mobileBanking.isSelected()) services += " Mobile Banking";
        if (alerts.isSelected()) services += " Alerts";
        if (chequeBook.isSelected()) services += " Cheque Book";
        if (eStatement.isSelected()) services += " E-Statement";

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/smartbanksystem", "root", "Parmarsatyam09@#");

            // Insert into signup3 table
            String query1 = "INSERT INTO signup3 VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst1 = conn.prepareStatement(query1);
            pst1.setString(1, formNo);
            pst1.setString(2, accountType);
            pst1.setString(3, cardNumber);
            pst1.setString(4, pin);
            pst1.setString(5, services.trim());
            pst1.executeUpdate();

            // 🔥 Insert into loginss table
            String query2 = "INSERT INTO loginss (card_number, pin) VALUES (?, ?)";
            PreparedStatement pst2 = conn.prepareStatement(query2);
            pst2.setString(1, cardNumber);
            pst2.setString(2, pin);
            pst2.executeUpdate();

            JOptionPane.showMessageDialog(this, "Account Created!\nCard No: " + cardNumber + "\nPIN: " + pin);

            setVisible(false);
            new Deposit(pin).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        new Signup3(1234567890L + "");
    }
}