package com.example.isa_yshtghl;

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

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PH.db";
    private static final int DB_VERSION = 4;
    private static String DB_PATH = "";
    private Context mContext = null;
    private SQLiteDatabase mDatabase;

    public DbHelper(Context context) {
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
    public List<String> getAllRestraunts(){
        List<String> temp = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM restaurant", null);
            if(c==null) return null;
            c.moveToFirst();
            do{
                String restaurant = c.getString(1);
                temp.add(restaurant);
            }
            while (c.moveToNext());{
                c.close();
            }
            return temp;
        }
        catch (Exception ex){}
        return temp;
    }


    // select all
/*
    public List<User> getAllUsers(){
        List<User> temp = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM User", null);
            if(c==null) return null;

            c.moveToFirst();
            do{
                User user = new User(c.getString(c.getColumnIndex("mail")), c.getString(c.getColumnIndex("ID")));
                temp.add(user);
            }
            while (c.moveToNext());{
                c.close();
            }
            return temp;
        }
        catch (Exception ex){}
        return temp;
    }

    public String validateLogin(String x, String y) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String id = "-1";
        try {
        String sql = "SELECT * FROM User WHERE mail = ? AND password = ?";
        c = db.rawQuery(sql ,new String[]{x,y} );
        if (c.isAfterLast()){
            db.close();
            return "-1";
        }
        c.moveToFirst();
        String sid = c.getString(c.getColumnIndex("mail"));
        id = c.getString(0);
        Log.d("new" , id);
        Integer.parseInt(sid);
        }
        catch (Exception ex){}
        db.close();
        return id;
    }

    public void insertUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int id = 0;
        String sql = "SELECT * FROM User";
        c = db.rawQuery(sql, null );
        if (c.isAfterLast()){
        }
        else{
            c.moveToLast();
            Log.d("ID" , c.getString(0));
            id = Integer.parseInt(c.getString(0)) + 1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("mail", user.getMail());
        contentValues.put("password", user.getPassword());
        contentValues.put("discriminator", user.getDiscriminator());
        contentValues.put("name", user.getName());
        contentValues.put("Phone", user.getPhone());
        contentValues.put("Address", user.getAddress());



        long result = db.insert("User",null, contentValues);
        Log.i("insert", String.valueOf(result));


        //sql = "INSERT INTO User  (ID , mail, password, discriminator, name, Phone, Address ) VALUES( ?, ?, ?, ?, ?, ?, ?)";
        //db.rawQuery(sql, new String[]{Integer.toString(id), user.getMail(), user.getPassword(), user.getDiscriminator(), user.getName(), user.getPhone(), user.getAddress()});
        db.close();

        Log.i("PATHHHH", DB_PATH);
    }
*/
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
