
package apuhostelmanagementfeessystem;

public class Resident extends User{
    private String roomNumber;
    private int stayYears;

    public Resident(String username, String name, String password, String role, String phone, String email, String status, String roomNumber, int stayYears) {
        super(username, name, password, role, phone, email, status);
        this.roomNumber = roomNumber;
        this.stayYears = stayYears;
    }
    

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getStayYears() {
        return stayYears;
    }

    public void setStayYears(int stayYears) {
        this.stayYears = stayYears;
    }
 
}
