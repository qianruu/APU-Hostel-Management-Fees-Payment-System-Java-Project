package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;


public class ResidentFunctions {
    //View payment history
    public static void viewPaymentHistory() {
        JFrame frame = new JFrame("Payment History");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Color textGreen = new Color(117, 132, 103); 
        frame.getContentPane().setBackground(Color.WHITE);
        
        //Room details
        Room room = DataIO.searchRoomByOwner(HostelManagementFees.loginUser);
        Panel roomPanel = new Panel(new GridLayout(4, 1));
        if (room != null) {
            Label roomNumber = new Label("Room Number: " + room.getRoom());
            roomNumber.setForeground(textGreen);
            Label roomOwnedDate = new Label("Owned Date: " + room.getOwnedDate());
            roomOwnedDate.setForeground(textGreen);
            Label roomPaymentDueDate = new Label("Next Payment Due Date: " + room.getDueDate());
            roomPaymentDueDate.setForeground(textGreen);
            Label roomEndDate = new Label("End Date: " + room.getEndDate());
            roomEndDate.setForeground(textGreen);
            roomPanel.add(roomNumber);
            roomPanel.add(roomOwnedDate);
            roomPanel.add(roomPaymentDueDate);
            roomPanel.add(roomEndDate);
        } else {
            roomPanel.add(new Label("No room details available."));
        }
        frame.add(roomPanel, BorderLayout.NORTH);
        
        //Payment history 
        JTextArea paymentHistoryArea = new JTextArea();
        paymentHistoryArea.setEditable(false);
        paymentHistoryArea.setText(HostelManagementFees.loginUser.getName() + "'s Payment History\n\n");

        for (Payment payment : DataIO.allPayments) {
            if (payment.getResident().getName().equals(HostelManagementFees.loginUser.getName())) {
                paymentHistoryArea.append("Payment ID: " + payment.getPaymentID() + "\n");
                paymentHistoryArea.append("Number of Months of Fees Collected: " + payment.getNumberOfMonths() + "\n");
                paymentHistoryArea.append("Amount to Be Collected: RM" + payment.getAmountToCollect() + "\n");
                paymentHistoryArea.append("Amount Collected: RM" + payment.getPaymentAmount() + "\n");
                paymentHistoryArea.append("Change: RM" + payment.getChange() + "\n");
                paymentHistoryArea.append("Payment Made Date: " + payment.getPaymentDate() + "\n\n");
            }
        }

        frame.add(new JScrollPane(paymentHistoryArea), BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

            
    //Update room
     public static void updateRoom() {
        JFrame frame = new JFrame("Update Room");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      
        frame.getContentPane().setBackground(Color.WHITE);

        String currentUser = HostelManagementFees.loginUser.getUsername();
        Room currentRoom = DataIO.searchRoomByOwner(HostelManagementFees.loginUser);
        
        //Current room details
        Panel roomPanel = new Panel(new GridLayout(4, 1));
        if (currentRoom != null) {
            Label roomNumberLabel = new Label("Room Number: " + currentRoom.getRoom());
            roomNumberLabel.setForeground(textGreen);
            Label ownedDateLabel = new Label("Owned Date: " + currentRoom.getOwnedDate());
            ownedDateLabel.setForeground(textGreen);
            Label dueDateLabel = new Label("Next Payment Due Date: " + currentRoom.getDueDate());
            dueDateLabel.setForeground(textGreen);
            Label endDateLabel = new Label("End Date: " + currentRoom.getEndDate());
            endDateLabel.setForeground(textGreen);
            roomPanel.add(roomNumberLabel);
            roomPanel.add(ownedDateLabel);
            roomPanel.add(dueDateLabel);
            roomPanel.add(endDateLabel);
        } else {
            roomPanel.add(new Label("No room details found."));
        }

        frame.add(roomPanel, BorderLayout.NORTH);
        
        //Request room changes
        Button changeRoomButton = new Button("Room Change Request");
        changeRoomButton.setBackground(buttonGreen);
        changeRoomButton.setForeground(Color.WHITE);
        changeRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentRoom == null) {
                    JOptionPane.showMessageDialog(frame, "You currently do not have a room assigned.");
                    return;
                }

                ArrayList<String> availableRooms = DataIO.getAvailableRooms();
                if (availableRooms.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No available rooms at the moment.");
                    return;
                }

                JFrame roomSelectionFrame = new JFrame("Select New Room");
                roomSelectionFrame.setSize(300, 200);
                roomSelectionFrame.setLayout(new FlowLayout());
                roomSelectionFrame.getContentPane().setBackground(Color.WHITE);
                Color textGreen = new Color(117, 132, 103); 
                Color buttonGreen = new Color(156, 175, 136);

                Label instructionLabel = new Label("Select a new room:");
                instructionLabel.setForeground(textGreen);

                Choice roomChoice = new Choice();
                for (int i = 0; i < availableRooms.size(); i++) {
                    roomChoice.add(availableRooms.get(i));
                }

                Button confirmButton = new Button("Confirm");
                confirmButton.setBackground(buttonGreen);
                confirmButton.setForeground(Color.WHITE);
                confirmButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String selectedRoom = roomChoice.getSelectedItem();
                        if (selectedRoom != null) {
                            RoomChangeRequest request = new RoomChangeRequest(currentUser, "RoomChange",currentRoom.getRoom(), selectedRoom, null,null,"Pending");
                            DataIO.allRequests.add(request);
                            DataIO.write();

                            JOptionPane.showMessageDialog(frame, "Your room change request has been sent to the manager.");
                            roomSelectionFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please select a room.");
                        }
                    }
                });

                Button cancelButton = new Button("Cancel");
                cancelButton.setBackground(buttonGreen);
                cancelButton.setForeground(Color.WHITE);
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        roomSelectionFrame.dispose();
                    }
                });

                roomSelectionFrame.add(instructionLabel);
                roomSelectionFrame.add(roomChoice);
                roomSelectionFrame.add(confirmButton);
                roomSelectionFrame.add(cancelButton);

                roomSelectionFrame.setVisible(true);
                roomSelectionFrame.setLocationRelativeTo(null);
            }
        });
        
        //Request room ending date change
        Button changeEndDateButton = new Button("Request End Date Change");
        changeEndDateButton.setBackground(buttonGreen);
        changeEndDateButton.setForeground(Color.WHITE);
        changeEndDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentRoom == null) {
                    JOptionPane.showMessageDialog(frame, "You currently do not have a room assigned.");
                    return;
                }
                
                //if payment status is fully paid, no changes can be held
                boolean isFullyPaid = false;
                for (int i = 0; i < DataIO.allPayments.size(); i++) {
                    Payment payment = DataIO.allPayments.get(i);
                    if (payment.getResident().getUsername().equals(currentUser) && payment.getPaymentStatus().equals("Fully Paid")) {
                        isFullyPaid = true;
                        break;
                    }
                }

                if (isFullyPaid) {
                    JOptionPane.showMessageDialog(frame, "You cannot request an end date change as your payment status is 'Fully Paid'.");
                    return;
                }
                
                String newEndDate = JOptionPane.showInputDialog(frame, "Enter the new end date (yyyy-mm-dd):");
                if (newEndDate != null && isValidDate(newEndDate)) {
                    RoomChangeRequest endDateRequest = new RoomChangeRequest(currentUser, "EndDateChange", currentRoom.getRoom(),null,currentRoom.getEndDate(),LocalDate.parse(newEndDate),"Pending");

                    DataIO.allRequests.add(endDateRequest);
                    DataIO.write();

                    JOptionPane.showMessageDialog(frame, "Your end date change request has been sent to the manager.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please try again.");
                }
            }
        });

        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(changeRoomButton);
        buttonPanel.add(changeEndDateButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

    