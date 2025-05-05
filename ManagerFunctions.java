
package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

public class ManagerFunctions {

    //Approve user
    public static void approveUser() {
        JFrame frame = new JFrame("Approve Users");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        Color textGreen = new Color(117, 132, 103);
        Color buttonGreen = new Color(156, 175, 136);
        frame.getContentPane().setBackground(Color.WHITE);

        //show pending users
        List pendingUsersList = new List();
        for (int i = 0; i < DataIO.allUsers.size(); i++) {
            User user = DataIO.allUsers.get(i);
            if (user.getStatus().equals("Pending")) {
                String roomNumber;
                if (user instanceof Resident) {
                    roomNumber = ((Resident) user).getRoomNumber();
                } else {
                    roomNumber = "null";
                }
                pendingUsersList.add(user.getUsername() + "    (" + user.getName() + "     Role: " + user.getRole() + "     Room: " + roomNumber + ")");
            }
        }

        Button approveButton = new Button("Approve");
        approveButton.setBackground(buttonGreen);
        approveButton.setForeground(Color.WHITE);
        Button rejectButton = new Button("Reject");
        rejectButton.setBackground(buttonGreen);
        rejectButton.setForeground(Color.WHITE);

        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedUser = pendingUsersList.getSelectedItem();
                if (selectedUser != null) {
                    String username = selectedUser.split(" ")[0];
                    User userToApprove = DataIO.searchByUsername(username);
                    if (userToApprove != null) {
                        userToApprove.setStatus("Approved");
                        if (userToApprove instanceof Resident) {
                            Resident resident = (Resident) userToApprove;
                            Room room = DataIO.searchRoomByNumber(resident.getRoomNumber());
                            if (room != null) {
                                room.setOwner(resident);
                                room.setOwnedDate(LocalDate.now());
                                room.setDueDate(LocalDate.now().plusMonths(1));
                                room.setEndDate(LocalDate.now().plusYears(resident.getStayYears()));
                                room.setIsAvailable(false);
                            }
                        }
                        DataIO.write();
                        pendingUsersList.remove(selectedUser);
                        JOptionPane.showMessageDialog(frame, "User approved successfully!");

                        // Send approval email
                        String email = userToApprove.getEmail();
                        String subject = "Account Approved";
                        String body = "Dear " + userToApprove.getName() + ",\n\n"
                                + "Your account has been approved.\n"
                                + "You can now access your profile and enjoy our services.\n"
                                + "Best regards,\nAPU Hostel";
                        try {
                            EmailSender.sendEmail(email, subject, body);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Failed to send approval email.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a user to approve.");
                }
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedUser = pendingUsersList.getSelectedItem();
                if (selectedUser != null) {
                    String username = selectedUser.split(" ")[0];
                    User userToReject = DataIO.searchByUsername(username);
                    if (userToReject != null) {
                        userToReject.setStatus("Rejected");
                        DataIO.write();
                        pendingUsersList.remove(selectedUser);
                        JOptionPane.showMessageDialog(frame, "User rejected successfully!");

                        //send reject email
                        String email = userToReject.getEmail();
                        String subject = "Account Rejected";
                        String body = "Dear " + userToReject.getName() + ",\n\n"
                                + "We regret to inform you that your account application has been rejected.\n"
                                + "For more information, please contact our support team.\n\n"
                                + "Best Regards,\nAPU Hostel";
                        EmailSender.sendEmail(email, subject, body);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a user to reject.");
                }
            }
        });

        Panel buttonPanel = new Panel();
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);

        frame.add(pendingUsersList, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    //update rate
    public static void updateRate() {
        JFrame frame = new JFrame("Update Rates");
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());
        Color textGreen = new Color(117, 132, 103);
        Color buttonGreen = new Color(156, 175, 136);
        frame.getContentPane().setBackground(Color.WHITE);

        Label currentRateLabel = new Label("Current Monthly Rate: RM" + DataIO.currentRate);
        currentRateLabel.setForeground(textGreen);
        TextField rateField = new TextField(10);
        Button updateButton = new Button("Update");
        updateButton.setBackground(buttonGreen);
        updateButton.setForeground(Color.WHITE);

        frame.add(currentRateLabel);
        frame.add(rateField);
        frame.add(updateButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double newRate = Double.parseDouble(rateField.getText());
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure to update the rate?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        DataIO.currentRate = newRate;
                        DataIO.write();
                        currentRateLabel.setText("Current Rate: RM" + DataIO.currentRate);
                        JOptionPane.showMessageDialog(frame, "Rate updated successfully!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input!");
                }
            }
        });
    }

    //manage all accounts
    public static void manageAccounts() {
        JFrame frame = new JFrame("Manage Accounts");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        Color textGreen = new Color(117, 132, 103);
        Color buttonGreen = new Color(156, 175, 136);
        frame.getContentPane().setBackground(Color.WHITE);

        //search user's name to view and update
        Panel searchPanel = new Panel(new BorderLayout());
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setBackground(buttonGreen);
        searchButton.setForeground(Color.WHITE);
        Label searchLabel = new Label("Search by Name: ");
        searchLabel.setForeground(textGreen);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        TextArea userDetailsArea = new TextArea();
        userDetailsArea.setEditable(false);

        Button updateRoleButton = new Button("Update Role");
        updateRoleButton.setBackground(buttonGreen);
        updateRoleButton.setForeground(Color.WHITE);
        Button updateStatusButton = new Button("Update Status");
        updateStatusButton.setBackground(buttonGreen);
        updateStatusButton.setForeground(Color.WHITE);
        Panel actionPanel = new Panel();
        actionPanel.add(updateRoleButton);
        actionPanel.add(updateStatusButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchName = searchField.getText().trim();
                if (!searchName.isEmpty()) {
                    int userIndex = -1;

                    for (int i = 0; i < DataIO.allUsers.size(); i++) {
                        if (DataIO.allUsers.get(i).getName().equalsIgnoreCase(searchName)) {
                            userIndex = i;
                            break;
                        }
                    }

                    if (userIndex != -1) {
                        User foundUser = DataIO.allUsers.get(userIndex);

                        userDetailsArea.setText("Username: " + foundUser.getUsername() + "\n"
                                + "Name: " + foundUser.getName() + "\n"
                                + "Role: " + foundUser.getRole() + "\n"
                                + "Phone: " + foundUser.getPhone() + "\n"
                                + "Email: " + foundUser.getEmail() + "\n"
                                + "Status: " + foundUser.getStatus());

                        if (foundUser instanceof Resident) {
                            Resident resident = (Resident) foundUser;
                            userDetailsArea.append("\nRoom Number: " + resident.getRoomNumber()
                                    + "\nStay Years: " + resident.getStayYears());
                        }

                        updateRoleButton.setEnabled(true);
                        updateStatusButton.setEnabled(true);

                        //role update within manager and staff only
                        updateRoleButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Choice roleChoice = new Choice();
                                if (foundUser.getRole().equals("Staff")) {
                                    roleChoice.add("Manager");
                                } else if (foundUser.getRole().equals("Manager")) {
                                    roleChoice.add("Staff");
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Role update is only allowed between Staff and Manager.");
                                    return;
                                }

                                roleChoice.select(foundUser.getRole());

                                Panel panel = new Panel();
                                panel.add(new Label("Select New Role:"));
                                panel.add(roleChoice);

                                int option = JOptionPane.showConfirmDialog(
                                        frame,
                                        panel,
                                        "Update Role",
                                        JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE
                                );

                                if (option == JOptionPane.OK_OPTION) {
                                    String selectedRole = roleChoice.getSelectedItem();

                                    if (!foundUser.getRole().equals(selectedRole)) {
                                        foundUser.setRole(selectedRole);
                                        DataIO.write();

                                        JOptionPane.showMessageDialog(frame, "Role updated successfully!");

                                        userDetailsArea.setText("Username: " + foundUser.getUsername() + "\n"
                                                + "Name: " + foundUser.getName() + "\n"
                                                + "Role: " + foundUser.getRole() + "\n"
                                                + "Phone: " + foundUser.getPhone() + "\n"
                                                + "Email: " + foundUser.getEmail() + "\n"
                                                + "Status: " + foundUser.getStatus());
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "Role is already set to the selected value.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Role update canceled.");
                                }
                            }
                        });

                        //deactivate account
                        updateStatusButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                int confirm = JOptionPane.showConfirmDialog(frame,
                                        "Are you sure you want to mark this user as inactive?", "Confirm Status Update",
                                        JOptionPane.YES_NO_OPTION);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    foundUser.setStatus("Inactive");
                                    DataIO.write();
                                    JOptionPane.showMessageDialog(frame, "User status updated to 'Inactive'!");
                                    userDetailsArea.setText("Username: " + foundUser.getUsername() + "\n"
                                            + "Name: " + foundUser.getName() + "\n"
                                            + "Role: " + foundUser.getRole() + "\n"
                                            + "Phone: " + foundUser.getPhone() + "\n"
                                            + "Email: " + foundUser.getEmail() + "\n"
                                            + "Status: " + foundUser.getStatus());
                                    updateRoleButton.setEnabled(false);
                                    updateStatusButton.setEnabled(false);
                                }
                            }
                        });

                    } else {
                        JOptionPane.showMessageDialog(frame, "User not found. Please try again.");
                        userDetailsArea.setText("");
                        updateRoleButton.setEnabled(false);
                        updateStatusButton.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a name to search.");
                }
            }
        });

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(userDetailsArea, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        updateRoleButton.setEnabled(false);
        updateStatusButton.setEnabled(false);

        frame.setVisible(true);
    }

    //manage all rooms
    public static void manageRooms() {
        JFrame frame = new JFrame("Manage Rooms");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        Color buttonGreen = new Color(156, 175, 136);
        frame.getContentPane().setBackground(Color.WHITE);

        List roomList = new List();
        updateRoomList(roomList);

        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setBackground(buttonGreen);
        viewDetailsButton.setForeground(Color.WHITE);
        Button addRoomButton = new Button("Add Room");
        addRoomButton.setBackground(buttonGreen);
        addRoomButton.setForeground(Color.WHITE);
        Button roomChangeButton = new Button("Room Change Requests");
        roomChangeButton.setBackground(buttonGreen);
        roomChangeButton.setForeground(Color.WHITE);

        // View Room Details
        viewDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedRoom = roomList.getSelectedItem();
                if (selectedRoom != null) {
                    String roomNumber = selectedRoom.split(" ")[1];
                    Room room = DataIO.searchRoomByNumber(roomNumber);
                    if (room != null) {
                        String details = "Room Number: " + room.getRoom() + "\n";
                        if (room.getIsAvailable()) {
                            details += "Availability: Available\n";
                        } else {
                            details += "Availability: Occupied\n";
                        }
                        if (room.getOwner() != null) {
                            details += "Owner: " + room.getOwner().getName() + "\n";
                        } else {
                            details += "Owner: None\n";
                        }
                        if (room.getOwnedDate() != null) {
                            details += "Owned Date: " + room.getOwnedDate() + "\n";
                        } else {
                            details += "Owned Date: N/A\n";
                        }
                        if (room.getEndDate() != null) {
                            details += "End Date: " + room.getEndDate() + "\n";
                        } else {
                            details += "End Date: N/A\n";
                        }
                        if (room.getDueDate() != null) {
                            details += "Due Date: " + room.getDueDate() + "\n";
                        } else {
                            details += "Due Date: N/A\n";
                        }
                        JOptionPane.showMessageDialog(frame, details, "Room Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a room to view details.");
                }
            }
        });

        // Add Room
        addRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomNumber = JOptionPane.showInputDialog(frame, "Enter Room Number:");
                if (roomNumber != null) {
                    if (isNumeric(roomNumber)) {
                        if (DataIO.searchRoomByNumber(roomNumber) == null) {
                            Room newRoom = new Room(roomNumber, true, null, null, null, null);
                            DataIO.allRooms.add(newRoom);
                            DataIO.write();
                            roomList.add("Room " + roomNumber + " (Available)");
                            JOptionPane.showMessageDialog(frame, "Room added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Room number already exists!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Room number must contain digits only!");
                    }
                }
            }
        });

        // View Room changes
        roomChangeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<RoomChangeRequest> requests = new ArrayList<>(DataIO.allRequests);
                ArrayList<RoomChangeRequest> pendingRequests = new ArrayList<>();

                JFrame requestsFrame = new JFrame("Pending Requests");
                requestsFrame.setSize(600, 400);
                requestsFrame.setLayout(new BorderLayout());
                requestsFrame.setLocationRelativeTo(null);
                Color buttonGreen = new Color(156, 175, 136);
                requestsFrame.getContentPane().setBackground(Color.WHITE);

                JTextArea requestsArea = new JTextArea();
                requestsArea.setEditable(false);

                Choice requestChoice = new Choice();

                updateRequestsAreaAndChoices(requests, pendingRequests, requestsArea, requestChoice);
                
                Button approveButton = new Button("Approve");
                approveButton.setBackground(buttonGreen);
                approveButton.setForeground(Color.WHITE);
                Button rejectButton = new Button("Reject");
                rejectButton.setBackground(buttonGreen);
                rejectButton.setForeground(Color.WHITE);

                approveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int selectedIndex = requestChoice.getSelectedIndex();
                        if (selectedIndex >= 0 && selectedIndex < pendingRequests.size()) {
                            RoomChangeRequest request = pendingRequests.get(selectedIndex);

                            if (request.getRequestType().equals("RoomChange")) {
                                User requestOwner = DataIO.searchByUsername(request.getUsername());
                                Room oldRoom = DataIO.searchRoomByOwner(requestOwner);
                                Room newRoom = DataIO.searchRoomByNumber(request.getRequestRoom());

                                if (newRoom.getIsAvailable()) {
                                    if (oldRoom != null) {
                                        // Move details to the new room
                                        newRoom.setOwner(requestOwner);
                                        newRoom.setIsAvailable(false);
                                        newRoom.setOwnedDate(oldRoom.getOwnedDate());
                                        newRoom.setDueDate(oldRoom.getDueDate());
                                        newRoom.setEndDate(oldRoom.getEndDate());
                                        if (requestOwner instanceof Resident) {
                                            ((Resident) requestOwner).setRoomNumber(newRoom.getRoom());
                                        }

                                        // Release the old room
                                        oldRoom.setOwner(null);
                                        oldRoom.setIsAvailable(true);
                                        oldRoom.setOwnedDate(null);
                                        oldRoom.setDueDate(null);
                                        oldRoom.setEndDate(null);

                                        request.setRoomChangeStatus("Approved");

                                        DataIO.write();
                                        JOptionPane.showMessageDialog(requestsFrame, "Room change request approved!");
                                        updateRequestsAreaAndChoices(requests, pendingRequests, requestsArea, requestChoice);
                                    } else {
                                        JOptionPane.showMessageDialog(requestsFrame, "No old room found for the user.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(requestsFrame, "Requested room is not available.");
                                }
                            } else if (request.getRequestType().equals("EndDateChange")) {
                                User requestOwner = DataIO.searchByUsername(request.getUsername());
                                Room room = DataIO.searchRoomByOwner(requestOwner);
                                if (room != null) {
                                    room.setEndDate(request.getRequestEndDate());
                                    request.setRoomChangeStatus("Approved");
                                    DataIO.write();
                                    JOptionPane.showMessageDialog(requestsFrame, "End date change request approved!");
                                    updateRequestsAreaAndChoices(requests, pendingRequests, requestsArea, requestChoice);
                                }
                            }
                        }
                    }
                });

                rejectButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int selectedIndex = requestChoice.getSelectedIndex();
                        if (selectedIndex >= 0 && selectedIndex < pendingRequests.size()) {
                            RoomChangeRequest request = pendingRequests.get(selectedIndex);
                            request.setRoomChangeStatus("Rejected");
                            DataIO.write();
                            JOptionPane.showMessageDialog(requestsFrame, "Request rejected.");
                            updateRequestsAreaAndChoices(requests, pendingRequests, requestsArea, requestChoice);
                        }
                    }
                });

                Panel actionsPanel = new Panel();
                actionsPanel.add(requestChoice);
                actionsPanel.add(approveButton);
                actionsPanel.add(rejectButton);

                requestsFrame.add(requestsArea, BorderLayout.CENTER);
                requestsFrame.add(actionsPanel, BorderLayout.SOUTH);

                requestsFrame.setVisible(true);
            }

            private void updateRequestsAreaAndChoices(ArrayList<RoomChangeRequest> requests, ArrayList<RoomChangeRequest> pendingRequests, JTextArea requestsArea, Choice requestChoice) {
                requestsArea.setText("");
                requestChoice.removeAll();
                pendingRequests.clear();

                if (!requests.isEmpty()) {
                    requestsArea.append("Pending Requests:\n");
                    for (RoomChangeRequest request : requests) {
                        String type = request.getRequestType();
                        if (type.equals("RoomChange") && request.getRoomChangeStatus().equals("Pending")) {
                            requestsArea.append("[Room Change] User: " + request.getUsername()
                                    + ", Current Room: " + request.getCurrentRoom()
                                    + ", Requested Room: " + request.getRequestRoom()
                                    + ", Status: " + request.getRoomChangeStatus() + "\n");
                            requestChoice.add(request.getRequestType() + ": " + request.getUsername());
                            pendingRequests.add(request);
                        } else if (type.equals("EndDateChange") && request.getRoomChangeStatus().equals("Pending")) {
                            requestsArea.append("[End Date Change] User: " + request.getUsername()
                                    + ", Current End Date: " + request.getCurrentEndDate()
                                    + ", Requested End Date: " + request.getRequestEndDate()
                                    + ", Status: " + request.getRoomChangeStatus() + "\n");
                            requestChoice.add(request.getRequestType() + ": " + request.getUsername());
                            pendingRequests.add(request);
                        }
                    }
                } else {
                    requestsArea.append("No Pending Requests.\n");
                }
            }
        });



        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(addRoomButton);
        buttonPanel.add(roomChangeButton);

        frame.add(roomList, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    
    private static void updateRoomList(List roomList) {
        roomList.removeAll();
        for (Room room : DataIO.allRooms) {
            if (room.getIsAvailable()) {
                roomList.add("Room " + room.getRoom() + " (Available)");
            } else {
                roomList.add("Room " + room.getRoom() + " (Occupied)");
            }
        }
    }
    

    //view all login records
    public static void viewLoginRecords() {
        JFrame frame = new JFrame("View Login Records");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        Color buttonGreen = new Color(156, 175, 136);
        frame.getContentPane().setBackground(Color.WHITE);

        List loginRecordsList = new List();
        for (int i = 0; i < DataIO.loginRecords.size(); i++) {
            Logs log = DataIO.loginRecords.get(i);
            loginRecordsList.add(log.getUsername() + " - " + log.getAction() + " - " + log.getTimestamp());
        }

        Button clearRecordsButton = new Button("Clear Records");
        clearRecordsButton.setBackground(buttonGreen);
        clearRecordsButton.setForeground(Color.WHITE);

        //delete all records
        clearRecordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all login records?");
                if (confirm == JOptionPane.YES_OPTION) {
                    DataIO.loginRecords.clear();
                    loginRecordsList.removeAll();
                    DataIO.write();
                    JOptionPane.showMessageDialog(frame, "Login records cleared!");
                }
            }
        });

        Panel buttonPanel = new Panel();
        buttonPanel.add(clearRecordsButton);

        frame.add(loginRecordsList, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
