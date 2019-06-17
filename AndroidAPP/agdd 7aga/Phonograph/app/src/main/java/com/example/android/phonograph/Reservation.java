package com.example.android.phonograph;


public class Reservation {
    private int ID;
    private int cusID;
    //private int customerID;
    private int noOfPeople;
    private int tableID;
    //private int resID;
    private String timeReserved;
    private String timeMade;

    public Reservation(int cusID, int noOfPeople, int tableID, String timeReserved, String timeMade) {
        this.cusID = cusID;
        this.noOfPeople = noOfPeople;
        this.tableID = tableID;
        this.timeReserved = timeReserved;
        this.timeMade = timeMade;
    }

    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setTimeReserved(String timeReserved) {
        this.timeReserved = timeReserved;
    }

    public void setTimeMade(String timeMade) {
        this.timeMade = timeMade;
    }

    public int getID() {
        return ID;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public int getTableID() {
        return tableID;
    }

    public String getTimeReserved() {
        return timeReserved;
    }

    public String getTimeMade() {
        return timeMade;
    }
}

