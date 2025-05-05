package apuhostelmanagementfeessystem;

import java.time.LocalDate;

public class Room {
    private String room;
    private boolean isAvailable;
    private User owner;
    private LocalDate ownedDate, endDate, dueDate;

    public Room(String room, boolean isAvailable, User owner, LocalDate ownedDate, LocalDate endDate, LocalDate dueDate) {
        this.room = room;
        this.isAvailable = isAvailable;
        this.owner = owner;
        this.ownedDate = ownedDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDate getOwnedDate() {
        return ownedDate;
    }

    public void setOwnedDate(LocalDate ownedDate) {
        this.ownedDate = ownedDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



    
}