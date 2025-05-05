package apuhostelmanagementfeessystem;

import java.time.LocalDate;

public class RoomChangeRequest {
    private String username, requestType,currentRoom,requestRoom;
    private LocalDate currentEndDate,requestEndDate;
    private String roomChangeStatus;

    public RoomChangeRequest(String username, String requestType, String currentRoom, String requestRoom, LocalDate currentEndDate, LocalDate requestEndDate, String roomChangeStatus) {
        this.username = username;
        this.requestType = requestType;
        this.currentRoom = currentRoom;
        this.requestRoom = requestRoom;
        this.currentEndDate = currentEndDate;
        this.requestEndDate = requestEndDate;
        this.roomChangeStatus = roomChangeStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getRequestRoom() {
        return requestRoom;
    }

    public void setRequestRoom(String requestRoom) {
        this.requestRoom = requestRoom;
    }

    public LocalDate getCurrentEndDate() {
        return currentEndDate;
    }

    public void setCurrentEndDate(LocalDate currentEndDate) {
        this.currentEndDate = currentEndDate;
    }

    public LocalDate getRequestEndDate() {
        return requestEndDate;
    }

    public void setRequestEndDate(LocalDate requestEndDate) {
        this.requestEndDate = requestEndDate;
    }

    public String getRoomChangeStatus() {
        return roomChangeStatus;
    }

    public void setRoomChangeStatus(String roomChangeStatus) {
        this.roomChangeStatus = roomChangeStatus;
    }

    
    
    
    
}
