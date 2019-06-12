package com.example.isa_yshtghl;
import android.util.Log;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionDB {
    private static  Connection conn;
    private static ConnectionDB instance;
    private ConnectionDB()
    {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\maria\\AndroidStudioProjects\\MyApplication4\\PH.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static ConnectionDB getInstance()
    {
        if(instance == null)
        {
            instance = new ConnectionDB();
        }
        return instance;
    }
    public static Connection getconnection() throws SQLException {
        Log.i("dkhl","dkhl");
        String url = "jdbc:sqlite:C:\\Users\\maria\\AndroidStudioProjects\\MyApplication4\\PH.db";
        Log.i("khrg","khrg");
        if(conn==null){
            Log.i("wslna","wslna");
            conn = DriverManager.getConnection(url);
            Log.i("wslna","wslna");}
        Log.i("wslna","wslna");
        return conn;
    }
}