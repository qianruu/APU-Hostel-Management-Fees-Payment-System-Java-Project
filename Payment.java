
package apuhostelmanagementfeessystem;

import java.time.LocalDate;

public class Payment {
    private String paymentID;
    private User resident;
    private Room room;
    private int numberOfMonths;
    private double amountToCollect;
    private double paymentAmount;
    private double change;
    private LocalDate paymentDate;
    private String paymentStatus;

    public Payment(String paymentID, User resident, Room room, int numberOfMonths, double amountToCollect, double paymentAmount, double change, LocalDate paymentDate, String paymentStatus) {
        this.paymentID = paymentID;
        this.resident = resident;
        this.room = room;
        this.numberOfMonths = numberOfMonths;
        this.amountToCollect = amountToCollect;
        this.paymentAmount = paymentAmount;
        this.change = change;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public User getResident() {
        return resident;
    }

    public void setResident(User resident) {
        this.resident = resident;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public double getAmountToCollect() {
        return amountToCollect;
    }

    public void setAmountToCollect(double amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

   
    }