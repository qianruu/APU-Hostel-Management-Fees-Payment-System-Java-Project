package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class LoginPage implements ActionListener {

    public void actionPerformed(ActionEvent e) {
    try {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                throw new Exception("Username or password cannot be empty.");
            }

            User found = DataIO.searchByUsername(username);
            if (found == null) {
                throw new Exception("User not found. Please register.");
            }

            if (!found.getPassword().equals(password)) {
                throw new Exception("Incorrect password.");
            }

            if (found.getStatus().equals("Pending")) {
                throw new Exception("Account is pending approval.");
            }
            
            if (found.getStatus().equals("Inactive")) {
                throw new Exception("Account is deactivated.");
            }

            HostelManagementFees.loginUser = found;
            DataIO.logAction(username, "logged in");

            navigateToDashboard(found.getRole());
            clearFields();

        } else if (e.getSource() == backButton) {
            frame.setVisible(false);
            if (HostelManagementFees.welcome != null) {
                HostelManagementFees.welcome.frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Welcome page is not initialized.");
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, ex.getMessage());
    }
}

private void navigateToDashboard(String role) {
    frame.setVisible(false);
    switch (role) {
        case "Resident":
            new ResidentLandingPage();
            break;
        case "Staff":
            new StaffLandingPage();
            break;
        case "Manager":
            new ManagerLandingPage();
            break;
        default:
            JOptionPane.showMessageDialog(frame, "Unknown role: " + role);
            break;
    }
}

    public JFrame frame;
    public Label titleLabel, usernameLabel, passwordLabel;
    public JTextField usernameField;
    public JPasswordField passwordField;
    Button loginButton, backButton;
    
    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public LoginPage() {
        frame = new JFrame("Login Page");
        frame.setLayout(null);
        frame.setSize(500, 400);
        
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      

        frame.getContentPane().setBackground(Color.WHITE);

        titleLabel = new Label("Login",Label.CENTER);
        titleLabel.setBounds(210, 130, 100, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textGreen); 
        frame.add(titleLabel);

        usernameLabel = new Label("Username:");
        usernameLabel.setBounds(100, 180, 80, 25);
        usernameLabel.setForeground(textGreen);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 180, 200, 25);
        frame.add(usernameField);

        passwordLabel = new Label("Password:");
        passwordLabel.setBounds(100, 220, 80, 25);
        passwordLabel.setForeground(textGreen);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 220, 200, 25);
        frame.add(passwordField);

        loginButton = new Button("Login");
        loginButton.setBounds(200, 260, 80, 30);
        loginButton.setBackground(buttonGreen);
        loginButton.setForeground(Color.WHITE);
        frame.add(loginButton);

        backButton = new Button("Back");
        backButton.setBounds(300, 260, 80, 30);
        backButton.setBackground(buttonGreen);
        backButton.setForeground(Color.WHITE);
        frame.add(backButton);

        loginButton.addActionListener(this);
        backButton.addActionListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
