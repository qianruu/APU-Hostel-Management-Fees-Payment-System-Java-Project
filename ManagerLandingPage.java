package apuhostelmanagementfeessystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;


public class ManagerLandingPage implements ActionListener {
    //Manager Dashboard
    public void actionPerformed(ActionEvent e){
        try{
            if(e.getSource() == approveUserButton){
                ManagerFunctions.approveUser();
            }else if (e.getSource() == updateRateButton){
                ManagerFunctions.updateRate();
            }else if (e.getSource() == manageAccountsButton){
                ManagerFunctions.manageAccounts();
            }else if (e.getSource() == manageRoomButton){
                ManagerFunctions.manageRooms();
            }else if (e.getSource() == loginRecordsButton){
                ManagerFunctions.viewLoginRecords();
            }else if (e.getSource() == stopButton){
                DataIO.write();
                System.exit(0);
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
    JFrame frame;
    Button approveUserButton, updateRateButton, manageAccountsButton, manageRoomButton, loginRecordsButton,stopButton,logoutButton;
    Label titleLabel;
    
    public ManagerLandingPage(){
        frame = new JFrame("Manager Landing Page");
        frame.setSize(500,500);
        frame.setLayout(null);
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      
        frame.getContentPane().setBackground(Color.WHITE);
        
        titleLabel = new Label("Manager Dashboard");
        titleLabel.setForeground(textGreen);
        titleLabel.setBounds(150,20,200,30);
        frame.add(titleLabel);
        
        approveUserButton = new Button("Approve User");
        approveUserButton.setBackground(buttonGreen);
        approveUserButton.setForeground(Color.WHITE);
        approveUserButton.setBounds(150,70,200,30);
        frame.add(approveUserButton);
        
        updateRateButton = new Button("Update Rates");
        updateRateButton.setBackground(buttonGreen);
        updateRateButton.setForeground(Color.WHITE);
        updateRateButton.setBounds(150,120,200,30);
        frame.add(updateRateButton);
        
        manageAccountsButton = new Button("Manage Accounts");
        manageAccountsButton.setBackground(buttonGreen);
        manageAccountsButton.setForeground(Color.WHITE);
        manageAccountsButton.setBounds(150,170,200,30);
        frame.add(manageAccountsButton);
        
        manageRoomButton = new Button("Manage Rooms");
        manageRoomButton.setBackground(buttonGreen);
        manageRoomButton.setForeground(Color.WHITE);
        manageRoomButton.setBounds(150,220,200,30);
        frame.add(manageRoomButton);
        
        loginRecordsButton = new Button("View Login Records");
        loginRecordsButton.setBackground(buttonGreen);
        loginRecordsButton.setForeground(Color.WHITE);
        loginRecordsButton.setBounds(150,270,200,30);
        frame.add(loginRecordsButton);
        
        stopButton = new Button("Stop System");
        stopButton.setBackground(buttonGreen);
        stopButton.setForeground(Color.WHITE);
        stopButton.setBounds(150,320,200,30);
        frame.add(stopButton);
        
        logoutButton = new Button("Logout");
        logoutButton.setBackground(buttonGreen);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBounds(150,370,200,30);
        frame.add(logoutButton);
        
        approveUserButton.addActionListener(this);
        updateRateButton.addActionListener(this);
        manageAccountsButton.addActionListener(this);
        manageRoomButton.addActionListener(this);
        loginRecordsButton.addActionListener(this);
        stopButton.addActionListener(this);
        logoutButton.addActionListener(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
