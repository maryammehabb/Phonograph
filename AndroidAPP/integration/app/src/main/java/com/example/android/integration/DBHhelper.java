package com.example.android.integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PH.db";
    private static final int DB_VERSION = 4;
    private static String DB_PATH = "";
    private Context mContext = null;
    private SQLiteDatabase mDatabase;

    public DBHhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if(Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        else
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        mContext = context ;

    }


    @Override
    public synchronized void close() {
        if(mDatabase != null)
            mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private  boolean checkDataBase(){
        SQLiteDatabase tempDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception ex){}

        if (tempDB != null)
            tempDB.close();
        return tempDB!=null?true:false;
    }

    public void copyDatabase(){
        try{
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length=myInput.read(buffer))>0){
                myOutput.write(buffer, 0 , length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDatabase(){
        String path = DB_PATH+DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDatabase(){
        boolean isDBExists = checkDataBase();
        if(isDBExists){

        }
        else
            this.getReadableDatabase();
        try{
            copyDatabase();
        }
        catch (Exception ex){

        }
    }


    public boolean insertOrder(Order order)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", order.getTime());
        contentValues.put("delivery", order.isDelivery());
        contentValues.put("price", order.getPrice());
        contentValues.put("restaurant_id",order.getRestaurant_id());
        contentValues.put("time_delivered",order.getTime_delivered());
        contentValues.put("done", order.isDone());
        contentValues.put("cusId", order.getCusId());
        contentValues.put("meals", order.getMeals());
        contentValues.put("numberOfMeals", order.getNumberOfMeals());
        long result = db.insert("Order",null, contentValues);
        if (result==-1)
            return true;
        else
            return false;

    }

    public void deleteOrder(String ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("order","id=?",new String[] {ID});
        db.close();

    }

    public void updateData (Order order,String ID,String meals, String numOfMeals)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", order.getId());
        contentValues.put("time", order.getTime());
        contentValues.put("delivery", order.isDelivery());
        contentValues.put("price", order.getPrice());
        contentValues.put("restaurant_id",order.getRestaurant_id());
        contentValues.put("time_delivered",order.getTime_delivered());
        contentValues.put("done", order.isDone());
        contentValues.put("cusId", order.getCusId());
        contentValues.put("meals", meals);
        contentValues.put("numberOfMeals", numOfMeals);
        db.update("Order",contentValues,"id=?",new String[] {ID});
    }


}
