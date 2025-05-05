package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ResidentLandingPage implements ActionListener{
    //resident dashboard
    public void actionPerformed(ActionEvent e){
        try{
            if(e.getSource() == updateProfileButton){
                Resident resident = (Resident) HostelManagementFees.loginUser;
                resident.updateAccount();
            }else if (e.getSource() == paymentHistoryButton){
                ResidentFunctions.viewPaymentHistory();
            }else if (e.getSource() == updateRoomButton){
                ResidentFunctions.updateRoom();
            }else if (e.getSource() == logoutButton){
                DataIO.logAction(HostelManagementFees.loginUser.getUsername(), "logged out");
                DataIO.write();
                frame.setVisible(false);
                new LoginRegister();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame,"Invalid input, try again!");
        }
    }
    
    public JFrame frame;
    public Label titleLabel, welcomeLabel;
    public Button updateProfileButton, paymentHistoryButton, updateRoomButton,logoutButton;
    
    public ResidentLandingPage(){
        frame = new JFrame("Resident Landing Page");
        frame.setLayout(null);
        frame.setSize(500, 400);
        
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      
        frame.getContentPane().setBackground(Color.WHITE);
        
        titleLabel = new Label("Resident Dashboard");
        titleLabel.setBounds(150, 20, 200, 30);
        titleLabel.setForeground(textGreen);
        frame.add(titleLabel);
        
        welcomeLabel = new Label("Welcome back " + HostelManagementFees.loginUser.getName()+"!");
        welcomeLabel.setBounds(150, 70, 200, 30);
        welcomeLabel.setForeground(textGreen);
        frame.add(welcomeLabel);
        
        updateProfileButton = new Button("Update Profile");
        updateProfileButton.setBounds(150, 120, 200, 30);
        updateProfileButton.setBackground(buttonGreen);
        updateProfileButton.setForeground(Color.WHITE);
        frame.add(updateProfileButton);
        
        paymentHistoryButton = new Button("View Payment History");
        paymentHistoryButton.setBounds(150, 170, 200, 30);
        paymentHistoryButton.setBackground(buttonGreen);
        paymentHistoryButton.setForeground(Color.WHITE);
        frame.add(paymentHistoryButton);
        
        updateRoomButton = new Button("Update Room");
        updateRoomButton.setBounds(150, 220, 200, 30);
        updateRoomButton.setBackground(buttonGreen);
        updateRoomButton.setForeground(Color.WHITE);
        frame.add(updateRoomButton);
        
        logoutButton = new Button("Logout");
        logoutButton.setBounds(150, 270, 200, 30);
        logoutButton.setBackground(buttonGreen);
        logoutButton.setForeground(Color.WHITE);
        frame.add(logoutButton);
        
        updateProfileButton.addActionListener(this);
        paymentHistoryButton.addActionListener(this);
        updateRoomButton.addActionListener(this);
        logoutButton.addActionListener(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
}
}
