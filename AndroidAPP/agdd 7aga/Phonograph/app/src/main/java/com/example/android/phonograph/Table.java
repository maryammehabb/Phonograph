package com.example.android.phonograph;

public class Table implements Comparable< Table >{
    String id;
    String BranchID;
    String Num_of_Seats;
    Boolean Reserved;
    public Table(String id, String branchID, String num_of_Seats, Boolean reserved) {
        this.id = id;
        BranchID = branchID;
        Num_of_Seats = num_of_Seats;
        Reserved = reserved;
    }

    @Override
    public int compareTo(Table o) {
        return this.Num_of_Seats.compareTo(o.Num_of_Seats);
    }
}
