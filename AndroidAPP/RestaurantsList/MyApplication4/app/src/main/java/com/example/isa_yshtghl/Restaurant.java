package com.example.isa_yshtghl;
import android.os.Build;
import android.util.Log;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Restaurant
{
    String name;
    int id;
    String timeStart;
    String timeEnd;
    String Locations;
    public  Restaurant(String name)
    {
        this.name=name;
    }/*
    public ArrayList<String> selectAll() throws SQLException {
        String sql = "SELECT name FROM restaurant ";
        ArrayList<String> all_restraunts= new ArrayList<String>();
        ConnectionDB  conDB = ConnectionDB.getInstance();
        if ( conDB==null)
        {
            Log.i("mariam" , "mariam");
        }
        else
        {

            Log.i("Marwaaaaaaa","Marwaaaaaaa");
        }
        Log.i("connection2","connection2");
        if(conDB.getconnection()==null)
        {
            Log.i("connection1","connection1");
        }
        else
        {
            Log.i("ooooooooooooooooooooooo" ,"ooooooooooooooooooooooo");
        }
        try (
             Connection con= conDB.getconnection();
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                all_restraunts.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all_restraunts;
    }*/
}