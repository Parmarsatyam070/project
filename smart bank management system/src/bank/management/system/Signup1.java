package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Signup1 extends JFrame implements ActionListener {
    JTextField nameField, fnameField, emailField, addressField, cityField, pinField , stateField;
    JRadioButton male, female, other;
    JComboBox<String> maritalBox;
    JFormattedTextField dobField;
    JButton nextBtn;
    String formNo;

    Signup1() {
        setTitle("Sign Up - Page 1");
        setLayout(null);

        formNo = "" + Math.abs(new Random().nextLong() % 9000 + 1000);

        JLabel formTitle = new JLabel("Application Form No. " + formNo);
        formTitle.setFont(new Font("Arial", Font.BOLD, 20));
        formTitle.setBounds(100, 30, 400, 30);
        formTitle.setForeground(Color.WHITE);
        add(formTitle);

        addLabel("Name:", 60); nameField = addField(60);
        addLabel("Father's Name:", 100); fnameField = addField(100);
        addLabel("DOB (YYYY-MM-DD):", 140);
        dobField = new JFormattedTextField(); dobField.setBounds(200, 140, 200, 25); add(dobField);
        addLabel("Gender:", 180);
        male = new JRadioButton("Male"); female = new JRadioButton("Female"); other = new JRadioButton("Other");
        male.setBounds(200, 180, 70, 25); female.setBounds(280, 180, 80, 25); other.setBounds(360, 180, 70, 25);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male); genderGroup.add(female); genderGroup.add(other);
        add(male); add(female); add(other);
        addLabel("Email:", 220); emailField = addField(220);
        addLabel("Marital Status:", 260);
        maritalBox = new JComboBox<>(new String[] {"Single", "Married", "Other"});
        maritalBox.setBounds(200, 260, 200, 25); add(maritalBox);
        addLabel("Address:", 300); addressField = addField(300);
        addLabel("City:", 340); cityField = addField(340);
        addLabel("PIN Code:", 420); pinField = addField(420);
        addLabel("State:", 380); stateField = addField(380);

        nextBtn = new JButton("Next");
        nextBtn.setBounds(200, 470, 100, 30);
        nextBtn.addActionListener(this); add(nextBtn);

        getContentPane().setBackground(new Color(30, 30, 40));
        setSize(500, 550);
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

    private JTextField addField(int y) {
        JTextField field = new JTextField();
        field.setBounds(200, y, 200, 25);
        add(field);
        return field;
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String fname = fnameField.getText();
        String dob = dobField.getText();
        String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "Other");
        String email = emailField.getText();
        String marital = (String) maritalBox.getSelectedItem();
        String address = addressField.getText();
        String city = cityField.getText();
        String pin = pinField.getText();
        String state = stateField.getText();


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartbanksystem", "root", "Parmarsatyam09@#");
            String query = "INSERT INTO signup1 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, formNo);
            pst.setString(2 , name);
            pst.setString(3, fname);
            pst.setString(4, dob);
            pst.setString(5, gender);
            pst.setString(6, email);
            pst.setString(7, marital);
            pst.setString(8, address);
            pst.setString(9, city);
            pst.setString(10, pin);
            pst.setString(11,state);
            pst.executeUpdate();


            setVisible(false);
            new Signup2(formNo).setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Signup1();
    }
}