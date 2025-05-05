
package apuhostelmanagementfeessystem;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class DataIO {
    public static ArrayList<User> allUsers = new ArrayList<User>();
    public static ArrayList<Room> allRooms = new ArrayList<Room>();
    public static double currentRate;
    public static List<Logs> loginRecords = new ArrayList<>();
    public static ArrayList<Payment> allPayments = new ArrayList<Payment>();
    public static ArrayList<RoomChangeRequest> allRequests = new ArrayList<RoomChangeRequest>();
    
    public static void read(){
        try{
            Scanner s = new Scanner(new File("user.txt"));
            while(s.hasNext()){
                String username =s.nextLine();
                String name =s.nextLine();
                String password =s.nextLine();
                String role =s.nextLine();
                String phone =s.nextLine();
                String email = s.nextLine();
                String status =s.nextLine();
                String roomNumber = s.nextLine();
                int stayYears = Integer.parseInt(s.nextLine());
                s.nextLine();
                User u;
                if (role.equals("Resident")) {
                    u = new Resident(username, name, password, role, phone, email, status, roomNumber, stayYears);
                } else {
                    u = new User(username, name, password, role, phone, email, status);
                }
                allUsers.add(u);
            }
            s = new Scanner(new File("room.txt"));
            while (s.hasNext()) {
                String room = s.nextLine();
                boolean isAvailable = Boolean.parseBoolean(s.nextLine());
                User owner = DataIO.searchByUsername(s.nextLine());
                LocalDate ownedDate = null;
                if (s.hasNextLine()) {
                    String ownedDateStr = s.nextLine();
                    if (!ownedDateStr.equals("null")) {
                        ownedDate = LocalDate.parse(ownedDateStr);
                    }
                }
                LocalDate endDate = null;
                if (s.hasNextLine()) {
                    String endDateStr = s.nextLine();
                    if (!endDateStr.equals("null")) {
                        endDate = LocalDate.parse(endDateStr);
                    }
                }
                LocalDate dueDate = null;
                if (s.hasNextLine()) {
                    String dueDateStr = s.nextLine();
                    if (!dueDateStr.equals("null")) {
                        dueDate = LocalDate.parse(dueDateStr);
                    }
                }
                s.nextLine();
                Room r = new Room(room, isAvailable, owner, ownedDate, endDate, dueDate);
                allRooms.add(r);
            }
            
            for (int i = 0; i < allRooms.size(); i++) {
            Room room = allRooms.get(i);
            if (room.getEndDate() != null && room.getEndDate().isBefore(LocalDate.now())) {
                room.setIsAvailable(true); 
                room.setOwner(null);       
                room.setOwnedDate(null);  
                room.setDueDate(null);
                room.setEndDate(null);     
            }
        }
            
            s = new Scanner(new File("rate.txt"));
             if (s.hasNext()) {
                currentRate = Double.parseDouble(s.nextLine());
            }
             
            s = new Scanner(new File("loginRecords.txt"));
            while (s.hasNext()) {
                String logLine = s.nextLine();
                Logs log = Logs.fromString(logLine);
                if (log != null) {
                    loginRecords.add(log);
                }
            }
            s = new Scanner(new File("payment.txt"));
            while(s.hasNext()){
                String paymentID = s.nextLine();
                User resident = DataIO.searchByUsername(s.nextLine());
                Room room = DataIO.searchRoomByNumber(s.nextLine());
                int numberOfMonths = Integer.parseInt(s.nextLine());
                double amountToCollect = Double.parseDouble(s.nextLine());
                double paymentAmount = Double.parseDouble(s.nextLine());
                double change = Double.parseDouble(s.nextLine());
                LocalDate paymentDate = null;
                if (s.hasNextLine()) {
                    String paymentDateStr = s.nextLine();
                    if (!paymentDateStr.equals("null")) {
                        paymentDate = LocalDate.parse(paymentDateStr);
                    }
                }
                String paymentStatus = s.nextLine();
                s.nextLine();
                Payment p = new Payment(paymentID,resident,room,numberOfMonths,amountToCollect,paymentAmount,change,paymentDate,paymentStatus);
                allPayments.add(p);
            }
            
            s = new Scanner(new File("roomChangeRequest.txt"));
            while (s.hasNext()){
                String username = s.nextLine();
                String requestType = s.nextLine();
                String currentRoom = s.nextLine();
                String requestRoom = s.nextLine();
                LocalDate currentEndDate = null;
                if (s.hasNextLine()) {
                    String currentEndDateStr = s.nextLine();
                    if (!currentEndDateStr.equals("null")) {
                        currentEndDate = LocalDate.parse(currentEndDateStr);
                    }
                }
                LocalDate requestEndDate = null;
                if (s.hasNextLine()) {
                    String requestEndDateStr = s.nextLine();
                    if (!requestEndDateStr.equals("null")) {
                        requestEndDate = LocalDate.parse(requestEndDateStr);
                    }
                }
                String roomChangeStatus = s.nextLine();
                s.nextLine();
                RoomChangeRequest r = new RoomChangeRequest(username,requestType,currentRoom,requestRoom,currentEndDate,requestEndDate,roomChangeStatus);
                allRequests.add(r);
            }
             
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error in read .....");
        }
    }
        
    public static void write() {
        try {
            PrintWriter p = new PrintWriter("user.txt");
            for (int i = 0; i < allUsers.size(); i++) {
                User user = allUsers.get(i);
                p.println(user.getUsername());
                p.println(user.getName());
                p.println(user.getPassword());
                p.println(user.getRole());
                p.println(user.getPhone());
                p.println(user.getEmail());
                p.println(user.getStatus());
                if (user instanceof Resident) {
                    Resident resident = (Resident) user;
                    p.println(resident.getRoomNumber());
                    p.println(resident.getStayYears());
                } else {
                    p.println("null");
                    p.println(0);
                }
                p.println();
            }
            p.close();
            
            p = new PrintWriter("room.txt");
            for (int i = 0; i < allRooms.size(); i++) {
                p.println(allRooms.get(i).getRoom());
                p.println(allRooms.get(i).getIsAvailable());
                User owner = allRooms.get(i).getOwner();
                if (owner != null) {
                    p.println(owner.getUsername());
                } else {
                    p.println("No Owner");  
                }
                LocalDate ownedDate = allRooms.get(i).getOwnedDate();
                if (ownedDate != null){
                    p.println(allRooms.get(i).getOwnedDate().toString());
                } else {
                    p.println("null");
                }
                LocalDate endDate = allRooms.get(i).getEndDate();
                if (endDate != null){
                    p.println(allRooms.get(i).getEndDate().toString());
                } else {
                    p.println("null");
                }
                LocalDate dueDate = allRooms.get(i).getDueDate();
                if (dueDate != null){
                    p.println(allRooms.get(i).getDueDate().toString());
                } else {
                    p.println("null");
                }
                
                p.println();
            }
            p.close();
            
            p = new PrintWriter("rate.txt");
            p.println(currentRate);
            p.close();
            
            p = new PrintWriter("loginRecords.txt");
            for (int i = 0; i < loginRecords.size(); i++) {
                p.println(loginRecords.get(i).toString());
            }
            p.close();

                        
            p = new PrintWriter("payment.txt");
            for (int i =0;i< allPayments.size();i++){
               p.println(allPayments.get(i).getPaymentID());
               p.println(allPayments.get(i).getResident().getUsername());
               p.println(allPayments.get(i).getRoom().getRoom());
               p.println(allPayments.get(i).getNumberOfMonths());
               p.println(allPayments.get(i).getAmountToCollect());
               p.println(allPayments.get(i).getPaymentAmount());
               p.println(allPayments.get(i).getChange());
               p.println(allPayments.get(i).getPaymentDate().toString());
               p.println(allPayments.get(i).getPaymentStatus());
               p.println();
            }
            p.close();
            
            p = new PrintWriter("roomChangeRequest.txt");
            for(int i= 0;i<allRequests.size();i++){
                p.println(allRequests.get(i).getUsername());
                p.println(allRequests.get(i).getRequestType());
                p.println(allRequests.get(i).getCurrentRoom());
                p.println(allRequests.get(i).getRequestRoom());
                LocalDate currentEndDate = allRequests.get(i).getCurrentEndDate();
                if (currentEndDate != null) {
                    p.println(currentEndDate.toString());
                } else {
                    p.println("null");
                }

                LocalDate requestEndDate = allRequests.get(i).getRequestEndDate();
                if (requestEndDate != null) {
                    p.println(requestEndDate.toString());
                } else {
                    p.println("null");
                }
                p.println(allRequests.get(i).getRoomChangeStatus());
                p.println();
            }
            p.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in write .....");
        }
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        name = name.trim();
        if (name.length() < 2 || name.length() > 50) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }

            if (i > 0) {
                char previousChar = name.charAt(i - 1);
                if ((c == ' ' ) && (previousChar == ' ')) {
                    return false;
                }
            }
        }
        return true;
    }

    
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isLetter(ch)) {
                hasLetter = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSymbol = true;
            }

            if (hasLetter && hasNumber && hasSymbol) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");
        return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
    }

    public static boolean isValidPhone(String phone) {
        if (phone.length() < 10 || phone.length() > 15) {
            return false;
        }

        for (int i = 0; i < phone.length(); i++) {
            char ch = phone.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
    
    public static User searchByUsername(String s) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (s.equals(allUsers.get(i).getUsername())) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    
    public static ArrayList<String> getAvailableRooms(){
        ArrayList<String> availableRooms = new ArrayList<>();
        for (int i = 0; i < allRooms.size(); i++){
            if(allRooms.get(i).getIsAvailable()== true){
                availableRooms.add(allRooms.get(i).getRoom());
            }
        }
        return availableRooms;
    }
    
    public static Room searchRoomByOwner(User owner) {
    for (int i = 0; i < allRooms.size(); i++) {
        Room room = allRooms.get(i);
        if (room.getOwner() != null && room.getOwner().equals(owner)) {
            return room;
        }
    }
    return null; 
}
    public static Room searchRoomByNumber(String roomNumber) {
    for (int i = 0; i < allRooms.size(); i++) {
        Room room = allRooms.get(i);
        if (room.getRoom() != null && room.getRoom().equals(roomNumber)) {
            return room;
        }
    }
    return null; 
}
    public static Payment searchPaymentByID(String paymentID) {
    for (int i = 0; i < allPayments.size(); i++) {
        Payment payment = allPayments.get(i);
        if (payment.getPaymentID() != null && payment.getPaymentID().equals(paymentID)) {
            return payment;
        }
    }
    return null; 
}
    
    public static Payment searchPaymentByName(String name) {
    for (int i = 0; i < allPayments.size(); i++) {
        Payment payment = allPayments.get(i);
        if (payment.getResident().getName() != null && payment.getResident().getName().equals(name)) {
            return payment;
        }
    }
    return null; 
}
    
    public static void logAction(String username, String action) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Logs log = new Logs(username, action, timestamp);
        loginRecords.add(log);
        write(); 
    }
}

