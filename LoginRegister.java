package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class LoginRegister implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == registerButton) {
                frame.setVisible(false);
                new RegisterPage();
            } else if (e.getSource() == loginButton) {
                frame.setVisible(false);
                new LoginPage();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input, try again!");
        }
    }

    public JFrame frame;
    public Label titleLabel;
    public Button loginButton, registerButton;

    public LoginRegister() {
        frame = new JFrame("Welcome Page");

        frame.setLayout(null);
        frame.setSize(500, 400);

        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      

        frame.getContentPane().setBackground(Color.WHITE);

        titleLabel = new Label("Welcome to APU Hostel Management Fees System", Label.CENTER);
        titleLabel.setBounds(60, 100, 400, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(textGreen); 
        frame.add(titleLabel);

        loginButton = new Button("Login");
        loginButton.setBounds(150, 200, 100, 30);
        loginButton.setBackground(buttonGreen); 
        loginButton.setForeground(Color.WHITE);
        frame.add(loginButton);

        registerButton = new Button("Register");
        registerButton.setBounds(270, 200, 100, 30);
        registerButton.setBackground(buttonGreen); 
        registerButton.setForeground(Color.WHITE); 
        frame.add(registerButton);

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
