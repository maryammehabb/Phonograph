package com.example.android.phonograph;


import java.util.ArrayList;

public class Restaurant {
    String name;
    String  id;
    String timeStart;
    String timeEnd;
    boolean kidsMenu;
    ArrayList<item>menu;
    public Restaurant(String id,String name,String timeStart,String timeEnd,boolean kidsMenu)
    {
        this.name=name;
        this.id=id;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.kidsMenu=kidsMenu;
        this.menu=menu;
    }
    public Restaurant(String id,String name,String timeStart,String timeEnd,boolean kidsMenu,ArrayList<item>menu)
    {
        this.name=name;
        this.id=id;
        this.timeStart=timeStart;
        this.timeEnd=timeEnd;
        this.kidsMenu=kidsMenu;
        this.menu=menu;
    }
}
