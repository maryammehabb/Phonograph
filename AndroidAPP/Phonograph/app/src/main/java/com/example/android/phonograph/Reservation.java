package com.example.android.phonograph;


public class Reservation {
    private String ID;
    private String cusID;
    private int noOfPeople;
    private  String branchID;
    private String tableID;
    private String resID;
    private String timeReserved;

    public String getBranchID() {
        return branchID;
    }

    public String getResID() {
        return resID;
    }

    private String timeMade;
    public Reservation(String ID, String cusID, int noOfPeople, String branchID, String tableID, String resID, String timeReserved, String timeMade) {
        this.ID = ID;
        this.cusID = cusID;
        this.noOfPeople = noOfPeople;
        this.branchID = branchID;
        this.tableID = tableID;
        this.resID = resID;
        this.timeReserved = timeReserved;
        this.timeMade = timeMade;
    }

    public Reservation( String cusID, int noOfPeople, String tableID, String branchID, String resID, String timeReserved, String timeMade) {
        this.cusID = cusID;
        this.noOfPeople = noOfPeople;
        this.tableID = tableID;
        this.branchID = branchID;
        this.resID = resID;
        this.timeReserved = timeReserved;
        this.timeMade = timeMade;
    }


    public Reservation(String cusID, int noOfPeople, String tableID, String timeReserved, String timeMade) {
        this.cusID = cusID;
        this.noOfPeople = noOfPeople;
        this.tableID = tableID;
        this.timeReserved = timeReserved;
        this.timeMade = timeMade;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public void setTimeReserved(String timeReserved) {
        this.timeReserved = timeReserved;
    }

    public void setTimeMade(String timeMade) {
        this.timeMade = timeMade;
    }

    public String getID() {
        return ID;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public String getTableID() {
        return tableID;
    }

    public String getTimeReserved() {
        return timeReserved;
    }

    public String getTimeMade() {
        return timeMade;
    }
}

