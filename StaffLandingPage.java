package apuhostelmanagementfeessystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;


public class StaffLandingPage implements ActionListener{
    //Staff Dashboard
    public void actionPerformed(ActionEvent e){
        try{
            if(e.getSource() == updateAccountButton){
                User staff = HostelManagementFees.loginUser;
                staff.updateAccount();
            }else if (e.getSource() == clearPaymentButton){
                StaffFunctions.makePayment();
            }else if (e.getSource() == generateReceiptButton){
                StaffFunctions.generateReceipt();
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
    Button updateAccountButton, clearPaymentButton,generateReceiptButton,logoutButton;
    Label titleLabel;
    
    public StaffLandingPage(){
        frame = new JFrame("Staff Landing Page");
        frame.setSize(500,400);
        frame.setLayout(null);
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      
        frame.getContentPane().setBackground(Color.WHITE);
        
        titleLabel = new Label("Staff Dashboard");
        titleLabel.setForeground(textGreen);
        titleLabel.setBounds(150,20,200,30);
        frame.add(titleLabel);
        
        updateAccountButton = new Button("Update Account Details");
        updateAccountButton.setBackground(buttonGreen);
        updateAccountButton.setForeground(Color.WHITE);
        updateAccountButton.setBounds(150,70,200,30);
        frame.add(updateAccountButton);
        
        clearPaymentButton = new Button("Clear Payment");
        clearPaymentButton.setBackground(buttonGreen);
        clearPaymentButton.setForeground(Color.WHITE);
        clearPaymentButton.setBounds(150,120,200,30);
        frame.add(clearPaymentButton);
        
        generateReceiptButton = new Button("Generate Receipt");
        generateReceiptButton.setBackground(buttonGreen);
        generateReceiptButton.setForeground(Color.WHITE);
        generateReceiptButton.setBounds(150,170,200,30);
        frame.add(generateReceiptButton);
        
        logoutButton = new Button("Logout");
        logoutButton.setBackground(buttonGreen);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBounds(150,220,200,30);
        frame.add(logoutButton);
        
        updateAccountButton.addActionListener(this);
        clearPaymentButton.addActionListener(this);
        generateReceiptButton.addActionListener(this);
        logoutButton.addActionListener(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
