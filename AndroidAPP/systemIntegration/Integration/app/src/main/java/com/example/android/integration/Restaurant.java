package com.example.android.integration;

import java.util.ArrayList;

public class Restaurant {
    String name;
    String  id;
    String timeStart;
    String timeEnd;
    ArrayList<Branch> Branches;
    ArrayList<item>menu;
    public Restaurant(String id,String name,String timeStart,String timeEnd)
    {
        this.name=name;
        this.id=id;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
    }
    public Restaurant(String id,String name,String timeStart,String timeEnd,ArrayList<Branch> Locations,ArrayList<item>menu)
    {
        this.name=name;
        this.id=id;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.Branches=Locations;
        this.menu=menu;
    }
}
