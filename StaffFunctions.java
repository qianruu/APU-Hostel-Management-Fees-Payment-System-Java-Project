
package apuhostelmanagementfeessystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.time.LocalDate;
import javax.swing.*;

public class StaffFunctions {
    //settle resident payments
    public static void makePayment() {
    JFrame frame = new JFrame("Payment Processing");
    frame.setSize(500, 600);
    frame.setLayout(new BorderLayout());

    Color textGreen = new Color(117, 132, 103);
    Color buttonGreen = new Color(156, 175, 136);
    frame.getContentPane().setBackground(Color.WHITE);

    //get resident details from room number
    Panel searchPanel = new Panel();
    searchPanel.setLayout(new FlowLayout());
    Label searchLabel = new Label("Enter room number: ");
    searchLabel.setForeground(textGreen);
    TextField searchField = new TextField(10);
    Button searchButton = new Button("Search");
    searchButton.setBackground(buttonGreen);
    searchButton.setForeground(Color.WHITE);
    searchPanel.add(searchLabel);
    searchPanel.add(searchField);
    searchPanel.add(searchButton);
    frame.add(searchPanel, BorderLayout.NORTH);

    TextArea residentInfoArea = new TextArea(6, 40);
    residentInfoArea.setEditable(false);
    Panel residentInfoPanel = new Panel();
    residentInfoPanel.add(residentInfoArea);
    frame.add(residentInfoPanel, BorderLayout.CENTER);

    Panel paymentPanel = new Panel();
    paymentPanel.setLayout(new GridLayout(5, 2, 10, 10));
    Label monthsLabel = new Label("Months to pay: ");
    monthsLabel.setForeground(textGreen);
    TextField monthsField = new TextField(10);
    Label amountLabel = new Label("Total amount to collect: ");
    amountLabel.setForeground(textGreen);
    Label amountField = new Label("RM0.00");
    amountField.setForeground(textGreen);
    Label collectedLabel = new Label("Collected Amount: ");
    collectedLabel.setForeground(textGreen);
    TextField collectedField = new TextField(10);
    Label changeLabel = new Label("Change: ");
    changeLabel.setForeground(textGreen);
    Label changeDisplay = new Label("RM0.00");
    changeDisplay.setForeground(textGreen);

    Button payButton = new Button("Process Payment");
    payButton.setBackground(buttonGreen);
    payButton.setForeground(Color.WHITE);
    Button backButton = new Button("Back");
    backButton.setBackground(buttonGreen);
    backButton.setForeground(Color.WHITE);

    paymentPanel.add(monthsLabel);
    paymentPanel.add(monthsField);
    paymentPanel.add(amountLabel);
    paymentPanel.add(amountField);
    paymentPanel.add(collectedLabel);
    paymentPanel.add(collectedField);
    paymentPanel.add(changeLabel);
    paymentPanel.add(changeDisplay);
    paymentPanel.add(payButton);
    paymentPanel.add(backButton);
    frame.add(paymentPanel, BorderLayout.SOUTH);

    searchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String roomNumber = searchField.getText().trim();
            Room room = DataIO.searchRoomByNumber(roomNumber);
            if (room != null && !room.getIsAvailable()) {
                User resident = room.getOwner();
                if (resident instanceof Resident) {
                    residentInfoArea.setText("Resident Name: " + resident.getName() + "\n"
                            + "Room Number: " + room.getRoom() + "\n"
                            + "Room Owned Date: " + room.getOwnedDate() + "\n"
                            + "Room End Date: " + room.getEndDate() + "\n"
                            + "Room Next Fees Due Date: " + room.getDueDate() + "\n"
                            + "Monthly Rate: RM" + DataIO.currentRate);
                } else {
                    JOptionPane.showMessageDialog(frame, "Room owner is not a resident.");
                    residentInfoArea.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Room not found or room has no owner yet.");
                residentInfoArea.setText("");
            }
        }
    });

    backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });

    monthsField.addTextListener(new TextListener() {
        public void textValueChanged(TextEvent e) {
            try {
                int months = Integer.parseInt(monthsField.getText());
                if (months <= 0) {
                    amountField.setText("RM0.00");
                    return;
                }
                double amountToCollect = DataIO.currentRate * months;
                amountField.setText("RM" + String.format("%.2f", amountToCollect));
            } catch (NumberFormatException ex) {
                amountField.setText("RM0.00");
            }
        }
    });

    collectedField.addTextListener(new TextListener() {
        public void textValueChanged(TextEvent e) {
            try {
                int months = Integer.parseInt(monthsField.getText());
                if (months <= 0) {
                    changeDisplay.setText("RM0.00");
                    return;
                }
                double amountToCollect = DataIO.currentRate * months;
                double collectedAmount = Double.parseDouble(collectedField.getText());
                double change = collectedAmount - amountToCollect;
                changeDisplay.setText("RM" + String.format("%.2f", change));
            } catch (NumberFormatException ex) {
                changeDisplay.setText("RM0.00");
            }
        }
    });

    payButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                int months = Integer.parseInt(monthsField.getText());
                if (months <= 0) {
                    JOptionPane.showMessageDialog(frame, "Number of months must be greater than 0.");
                    return;
                }
                double collectedAmount = Double.parseDouble(collectedField.getText());
                double monthlyRate = DataIO.currentRate;
                double amountToCollect = monthlyRate * months;
                double change = collectedAmount - amountToCollect;

                if (change < 0) {
                    JOptionPane.showMessageDialog(frame, "Insufficient payment! Amount collected is less than the amount to collect.");
                    return;
                }
                
                //if payment status is fully paid, no changes can be held
                boolean isFullyPaid = false;
                String roomNumber = searchField.getText().trim();
                Room room = DataIO.searchRoomByNumber(roomNumber);
                User resident = room.getOwner();
                
                for (int i = 0; i < DataIO.allPayments.size(); i++) {
                    Payment payment = DataIO.allPayments.get(i);
                    if (payment.getResident().getUsername().equals(resident) && payment.getPaymentStatus().equals("Fully Paid")) {
                        isFullyPaid = true;
                        break;
                    }
                }

                if (isFullyPaid) {
                    JOptionPane.showMessageDialog(frame, "You cannot request an end date change as your payment status is 'Fully Paid'.");
                    return;
                }

                if (months > 12) {
                    JOptionPane.showMessageDialog(frame, "Resident can only settle a maximum of 1 year of management fees at once.");
                    return;
                }

                
                LocalDate currentDueDate = room.getDueDate();
                room.setDueDate(currentDueDate.plusMonths(months));

                String paymentID = "PAY" + (DataIO.allPayments.size() + 1001);
                LocalDate paymentDate = LocalDate.now();
                String paymentStatus = " ";
                if (room.getEndDate().isBefore(room.getDueDate()) || room.getEndDate().equals(room.getDueDate())) {
                    paymentStatus = "Fully Paid";
                    room.setDueDate(null);
                } else {
                    paymentStatus = "Partially Paid";
                }

                Payment newPayment = new Payment(paymentID, resident, room, months, amountToCollect, collectedAmount, change, paymentDate, paymentStatus);
                DataIO.allPayments.add(newPayment);
                DataIO.write();
                JOptionPane.showMessageDialog(frame, "Payment processed successfully!");
                
                String email = resident.getEmail();
                String subject = "Payment Confirmation";
                String body = "Dear " + resident.getName() + ",\n\n"
                        + "Your payment has been processed successfully.\n" +
                        "Payment ID: " + paymentID + "\n" +
                        "Number of months settled: " + months + "\n"+
                        "Amount to colect: RM" + amountToCollect +"\n"+
                        "Amount Collected: RM" + collectedAmount + "\n" +
                        "Change Returned: RM" + change + "\n" +
                        "Payment Date: " + paymentDate + "\n" +
                        "Payment Status: " + paymentStatus + "\n\n" +
                        "Thank you for your payment.\n\n" +
                        "Best regards,\n" +
                        "APU Hostel Management System";
                try {
                    EmailSender.sendEmail(email, subject, body);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to send payment confirmation email.");
                }
                        
                monthsField.setText("");
                collectedField.setText("");
                searchField.setText("");
                amountField.setText("RM0.00");
                changeDisplay.setText("RM0.00");
                residentInfoArea.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
            }
        }
    });

    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
}
    
    //generate receipt
    public static void generateReceipt() {
        JFrame frame = new JFrame("Generate Receipt");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        Color textGreen = new Color(117, 132, 103); 
        Color buttonGreen = new Color(156, 175, 136);      
        frame.getContentPane().setBackground(Color.WHITE);

        //retrieve payment from file with paymentid
        Panel searchPanel = new Panel(new FlowLayout());
        Label searchLabel = new Label("Enter Payment ID:");
        searchLabel.setForeground(textGreen);
        TextField searchField = new TextField(15);
        Button searchButton = new Button("Search");
        searchButton.setBackground(buttonGreen);
        searchButton.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        Panel receiptPanel = new Panel(new BorderLayout());
        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false); 
        receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        frame.add(receiptPanel, BorderLayout.CENTER);

        Panel buttonPanel = new Panel(new FlowLayout());
        Button printButton = new Button("Print Receipt");
        printButton.setBackground(buttonGreen);
        printButton.setForeground(Color.WHITE);
        printButton.setEnabled(false); 
        buttonPanel.add(printButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String paymentID = searchField.getText().trim();
                Payment payment = DataIO.searchPaymentByID(paymentID); 

                if (payment != null) {
                    receiptArea.setText(""); 
                    receiptArea.append("========== APU Hostel Management System ==========\n");
                    receiptArea.append("Payment Receipt\n");
                    receiptArea.append("------------------------------------------------\n");
                    receiptArea.append("Payment ID: " + payment.getPaymentID() + "\n");
                    receiptArea.append("Resident Name: " + payment.getResident().getName() + "\n");
                    receiptArea.append("Room Number: " + payment.getRoom().getRoom() + "\n");
                    receiptArea.append("Months Paid: " + payment.getNumberOfMonths() + "\n");
                    receiptArea.append("Total Amount To Be Collected: " +payment.getAmountToCollect() + "\n");
                    receiptArea.append("Total Amount Collected: RM" + payment.getPaymentAmount() + "\n");
                    receiptArea.append("Change Returned: RM" + payment.getChange() + "\n");
                    receiptArea.append("Payment Date: " + payment.getPaymentDate() + "\n");
                    receiptArea.append("Payment Status: " + payment.getPaymentStatus() + "\n");
                    receiptArea.append("================================================\n");

                    printButton.setEnabled(true); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Payment ID not found.");
                }
            }
        });

        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean printed = receiptArea.print(); 
                    if (printed) {
                        JOptionPane.showMessageDialog(frame, "Receipt printed successfully!");
                        receiptArea.setText("");//clear fields
                    } else {
                        JOptionPane.showMessageDialog(frame, "Printing canceled.");
                        receiptArea.setText("");//clear fields
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error while printing: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
    
    

