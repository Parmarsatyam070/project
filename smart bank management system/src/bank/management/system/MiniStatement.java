package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
        import java.awt.event.*;
        import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {
    JTable transactionTable;
    JButton backButton;
    String pinNumber;

    MiniStatement(String pinNumber) {
        this.pinNumber = pinNumber;

        setTitle("Mini Statement");
        setLayout(null);

        JLabel heading = new JLabel("Recent Transactions");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(180, 30, 300, 30);
        add(heading);

        // Table to show transactions
        String[] columnNames = {"Date", "Type", "Amount"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(80, 80, 400, 200);
        add(scrollPane);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(230, 300, 120, 35);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);
        add(backButton);

        // Setup frame
        getContentPane().setBackground(Color.WHITE);
        setSize(560, 400);
        setLocation(300, 200);
        setVisible(true);

        loadTransactions(tableModel);
    }

    private void loadTransactions(DefaultTableModel tableModel) {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM banks WHERE pin = '" + pinNumber + "' ORDER BY date DESC LIMIT 10");

            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amount = rs.getString("amount");

                tableModel.addRow(new Object[]{date, type, "₹" + amount});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading transactions.");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pinNumber);
    }

    public static void main(String[] args) {
        new MiniStatement("");
    }
}
