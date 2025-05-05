package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class RegisterPage implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == registerButton) {
                String username = usernameField.getText();
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());
                String role = roleDropdown.getSelectedItem();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String status = "Pending";
                String roomNumber = null;
                int stayYears = 0;

                if (role.equals("Resident")) {
                    roomNumber = roomDropdown.getSelectedItem();
                    if (roomNumber == null) {
                        JOptionPane.showMessageDialog(frame, "Please select a room.");
                        return;
                    }
                    try {
                        stayYears = Integer.parseInt(stayYearsField.getText());
                        if (stayYears <= 0) throw new NumberFormatException();
                        if (stayYears>4){
                            JOptionPane.showMessageDialog(frame, "Maximum stay years is 4 years.");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number of years.");
                        return;
                    }
                }

                User found = DataIO.searchByUsername(username);
                if (found != null) {
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                    return;
                }
                if (username.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!");
                } else if (!DataIO.isValidPassword(password)) {
                    JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long and include at least one letter, one number, and one symbol.");
                    return;
                }else if(!DataIO.isValidName(name)){
                    JOptionPane.showMessageDialog(frame, "Invalid name format! Please enter a valid name.");     
                    return;
                } else if (!DataIO.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format! Please enter a valid email.");
                    return;
                } else if (!DataIO.isValidPhone(phone)) {
                    JOptionPane.showMessageDialog(frame, "Invalid phone number! Please enter a valid number (10-15 digits).");
                    return;
                } else {
                    User newUser;
                    if (role.equals("Resident")) {
                        newUser = new Resident(username, name, password, role, phone, email, status, roomNumber, stayYears);
                    } else {
                        newUser = new User(username, name, password, role, phone, email, status);
                    }
                    DataIO.allUsers.add(newUser);
                    DataIO.write();
                    JOptionPane.showMessageDialog(frame, "User registered successfully! Waiting for approval.");
                    clearFields();
                    updateRoomAndStayFieldsVisibility();
                    frame.setVisible(false);
                    new LoginRegister();

                    if (role.equals("Resident")) {
                        Room room = DataIO.searchRoomByNumber(roomNumber);
                        room.setIsAvailable(false);
                        DataIO.write();
                    }
                }
            } else if (e.getSource() == backButton) {
                frame.setVisible(false);
                HostelManagementFees.welcome.frame.setVisible(true);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input, try again!");
        }
    }

    public JFrame frame;
    public Label titleLabel, usernameLabel, nameLabel, passwordLabel, roleLabel, phoneLabel, emailLabel,roomLabel, stayYearsLabel;
    public TextField usernameField, nameField, phoneField, emailField,stayYearsField;
    public JPasswordField passwordField;
    public Choice roleDropdown, roomDropdown;
    public Button registerButton, backButton;

    private void clearFields() {
        usernameField.setText("");
        nameField.setText("");
        passwordField.setText("");
        phoneField.setText("");
        emailField.setText("");
        stayYearsField.setText("");
    }

    public RegisterPage() {
        frame = new JFrame("User Registration");
        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      
        frame.getContentPane().setBackground(Color.WHITE);

        titleLabel = new Label("Register New User", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textGreen);
        titleLabel.setBounds(50, 20, 400, 40);
        frame.add(titleLabel);

        usernameLabel = new Label("Username:");
        usernameLabel.setBounds(50, 80, 100, 30);
        usernameLabel.setForeground(textGreen);
        frame.add(usernameLabel);

        usernameField = new TextField();
        usernameField.setBounds(160, 80, 250, 30);
        frame.add(usernameField);

        nameLabel = new Label("Name:");
        nameLabel.setBounds(50, 120, 100, 30);
        nameLabel.setForeground(textGreen);
        frame.add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(160, 120, 250, 30);
        frame.add(nameField);

        passwordLabel = new Label("Password:");
        passwordLabel.setBounds(50, 160, 100, 30);
        passwordLabel.setForeground(textGreen);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 160, 250, 30);
        frame.add(passwordField);

        phoneLabel = new Label("Phone:");
        phoneLabel.setBounds(50, 200, 100, 30);
        phoneLabel.setForeground(textGreen);
        frame.add(phoneLabel);

        phoneField = new TextField();
        phoneField.setBounds(160, 200, 250, 30);
        frame.add(phoneField);
        
        emailLabel = new Label("Email");
        emailLabel.setBounds(50,240,100,30);
        emailLabel.setForeground(textGreen);
        frame.add(emailLabel);
        
        emailField = new TextField();
        emailField.setBounds(160,240,250,30);
        frame.add(emailField);

        roleLabel = new Label("Role:");
        roleLabel.setBounds(50, 280, 100, 30);
        roleLabel.setForeground(textGreen);
        frame.add(roleLabel);

        roleDropdown = new Choice();
        roleDropdown.add("Resident");
        roleDropdown.add("Staff");
        roleDropdown.add("Manager");
        roleDropdown.setBounds(160, 280, 250, 30);
        frame.add(roleDropdown);

        roomLabel = new Label("Room Number:");
        roomLabel.setBounds(50, 320, 100, 30);
        roomLabel.setForeground(textGreen);
        frame.add(roomLabel);

        roomDropdown = new Choice();
        roomDropdown.setBounds(160, 320, 250, 30);
        frame.add(roomDropdown);
        roomDropdown.setVisible(false);

        stayYearsLabel = new Label("Stay Years:");
        stayYearsLabel.setBounds(50, 360, 100, 30);
        stayYearsLabel.setForeground(textGreen);
        frame.add(stayYearsLabel);

        stayYearsField = new TextField();
        stayYearsField.setBounds(160, 360, 250, 30);
        frame.add(stayYearsField);
        stayYearsField.setVisible(false);

        registerButton = new Button("Register");
        registerButton.setBounds(160, 400, 100, 30);
        registerButton.setBackground(buttonGreen);
        registerButton.setForeground(Color.WHITE);
        frame.add(registerButton);

        backButton = new Button("Back");
        backButton.setBounds(310, 400, 100, 30);
        backButton.setBackground(buttonGreen);
        backButton.setForeground(Color.WHITE);
        frame.add(backButton);
        
        
        roleDropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                updateRoomAndStayFieldsVisibility();
            }
        });

        registerButton.addActionListener(this);
        backButton.addActionListener(this);
        updateRoomAndStayFieldsVisibility();
        
    }
    
    private void populateRoomDropdown() {
        roomDropdown.removeAll();
        ArrayList<String> availableRooms = DataIO.getAvailableRooms();
        for (int i = 0; i < availableRooms.size(); i++) {
            roomDropdown.add(availableRooms.get(i));
        }
    }
    private void updateRoomAndStayFieldsVisibility() {
        if (roleDropdown.getSelectedItem().equals("Resident")) {
            populateRoomDropdown();
            roomDropdown.setVisible(true);
            roomLabel.setVisible(true);
            stayYearsLabel.setVisible(true);
            stayYearsField.setVisible(true);
        } else {
            roomDropdown.setVisible(false);
            roomLabel.setVisible(false);
            stayYearsLabel.setVisible(false);
            stayYearsField.setVisible(false);
        }
    }
}




