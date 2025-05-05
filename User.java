package apuhostelmanagementfeessystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class User {
    private String username,name,password,role,phone,email,status;

    public User(String username, String name, String password, String role, String phone, String email, String status) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.status = status;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public void updateAccount() {
        JFrame frame = new JFrame("Update Account");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 2));
        Color textGreen = new Color(117, 132, 103);
        Color buttonGreen = new Color(156, 175, 136);
        frame.getContentPane().setBackground(Color.WHITE);

        Label nameLabel = new Label("Name:");
        nameLabel.setForeground(textGreen);
        TextField nameField = new TextField(this.getName());
        Label usernameLabel = new Label("Username:");
        usernameLabel.setForeground(textGreen);
        TextField usernameField = new TextField(this.getUsername());
        Label passwordLabel = new Label("Password:");
        passwordLabel.setForeground(textGreen);
        JPasswordField passwordField = new JPasswordField(this.getPassword());
        Label emailLabel = new Label("Email:");
        emailLabel.setForeground(textGreen);
        TextField emailField = new TextField(this.getEmail());
        Label phoneLabel = new Label("Phone No:");
        phoneLabel.setForeground(textGreen);
        TextField phoneField = new TextField(this.getPhone());

        Button updateNameButton = new Button("Update Name");
        updateNameButton.setBackground(buttonGreen);
        updateNameButton.setForeground(Color.WHITE);
        Button updateUsernameButton = new Button("Update Username");
        updateUsernameButton.setBackground(buttonGreen);
        updateUsernameButton.setForeground(Color.WHITE);
        Button updatePasswordButton = new Button("Update Password");
        updatePasswordButton.setBackground(buttonGreen);
        updatePasswordButton.setForeground(Color.WHITE);
        Button updateEmailButton = new Button("Update Email");
        updateEmailButton.setBackground(buttonGreen);
        updateEmailButton.setForeground(Color.WHITE);
        Button updatePhoneButton = new Button("Update Phone");
        updatePhoneButton.setBackground(buttonGreen);
        updatePhoneButton.setForeground(Color.WHITE);
        Button cancelButton = new Button("Cancel");
        cancelButton.setBackground(buttonGreen);
        cancelButton.setForeground(Color.WHITE);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(updateNameButton);
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(updateUsernameButton);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(updatePasswordButton);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(updateEmailButton);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(updatePhoneButton);
        frame.add(new Label());
        frame.add(cancelButton);

        // Update name
        updateNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText().trim();
                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty!");
                    return;
                }
                if (!DataIO.isValidName(newName)) {
                    JOptionPane.showMessageDialog(frame, "Invalid name format! Please enter a valid name.");
                    return;
                }
                if (newName.equals(getName())) {
                    JOptionPane.showMessageDialog(frame, "Name is the same as current name. Please check your input.");
                    return;
                }
                setName(newName);
                DataIO.write();
                JOptionPane.showMessageDialog(frame, "Name updated successfully!");
            }
        });

        // Update username
        updateUsernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText().trim();
                if (newUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Username cannot be empty!");
                    return;
                }
                if (newUsername.equals(getUsername())) {
                    JOptionPane.showMessageDialog(frame, "Username is the same as current username. Please check your input.");
                    return;
                }
                User found = DataIO.searchByUsername(newUsername);
                if (found != null) {
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                    return;
                }

                setUsername(newUsername);
                DataIO.write();
                JOptionPane.showMessageDialog(frame, "Username updated successfully!");
            }
        });

        // Update password
        updatePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(passwordField.getPassword()).trim();
                if (!DataIO.isValidPassword(newPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long and include at least one letter, one number, and one symbol.");
                    return;
                }
                if (newPassword.equals(getPassword())) {
                    JOptionPane.showMessageDialog(frame, "Password is the same as current password. Please check your input.");
                    return;
                }
                setPassword(newPassword);
                DataIO.write();
                JOptionPane.showMessageDialog(frame, "Password updated successfully!");
            }
        });

        // Update email
        updateEmailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newEmail = emailField.getText().trim();
                if (!DataIO.isValidEmail(newEmail)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format! Please enter a valid email.");
                    return;
                }
                if (newEmail.equals(getEmail())) {
                    JOptionPane.showMessageDialog(frame, "Email is the same as current email. Please check your input.");
                    return;
                }
                setEmail(newEmail);
                DataIO.write();
                JOptionPane.showMessageDialog(frame, "Email updated successfully!");
            }
        });

        // Update phone number
        updatePhoneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newPhone = phoneField.getText().trim();
                if (!DataIO.isValidPhone(newPhone)) {
                    JOptionPane.showMessageDialog(frame, "Invalid phone number! Please enter a valid number (10-15 digits).");
                    return;
                }
                if (newPhone.equals(getPhone())) {
                    JOptionPane.showMessageDialog(frame, "Phone number is the same as current phone number. Please check your input.");
                    return;
                }
                setPhone(newPhone);
                DataIO.write();
                JOptionPane.showMessageDialog(frame, "Phone number updated successfully!");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
