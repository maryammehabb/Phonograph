package com.example.android.phonograph;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PH.db";
    private static final int DB_VERSION = 4;
    private static String DB_PATH = "";
    private Context mContext = null;
    private SQLiteDatabase mDatabase;
    String x = "Order";
    String User = "User";
    String restaurant = "restaurant";
    String Item = "Item";
    String y = "Table";
    String branch = "branch";
    String complaint = "complaint";
    String Reservation = "Reservation";
    String Admin = "Admin";

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if(Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        else
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        mContext = context ;

    }
    /*public void del(){
        mDatabase.execSQL("DROP TABLE IF EXISTS order " + ;
        mDatabase.execSQL("DROP TABLE IF EXISTS " + User);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + Admin);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + restaurant);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + Item);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + y);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + branch);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + complaint);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + Reservation);
    }*/
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
    /*private void exportDB() {
        try{
            final String inFileName = "/data/data/com.example.android.phonograph/databases/PH.db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            //InputStream myInput = mContext.getAssets().open(DB_NAME);
            OutputStream myOutput = mContext.getAssets().open(DB_NAME,);

            byte[] buffer = new byte[1024];
            int length;
            while ((length=fis.read(buffer))>0){
                myOutput.write(buffer, 0 , length);
            }
            myOutput.flush();
            myOutput.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
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
    // select all
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
    public String[] validateLogin(String x, String y) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String id = "-1";
        String name = "";
        String ret[] = {id, name};

        //try {
        String sql = "SELECT * FROM User WHERE mail = ? AND password = ?";
        c = db.rawQuery(sql ,new String[]{x,y} );
        if (c.isAfterLast()){
            //db.close();
            return ret;
        }
        c.moveToFirst();
        //String sid = c.getString(c.getColumnIndex("mail"));
        id = c.getString(0);
        name = c.getString(3);
        Log.d("new" , id);
        //Integer.parseInt(sid);
        /*}
        catch (Exception ex){}*/
        //db.close();
        ret[0] = id;
        ret[1] = name;
        return ret;
    }
    public void insertUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        /*int id = 0;
        String sql = "SELECT * FROM User";
        c = db.rawQuery(sql, null );
        if (c.isAfterLast()){
        }
        else{
            c.moveToLast();
            Log.d("ID" , c.getString(0));
            id = Integer.parseInt(c.getString(0)) + 1;
        }*/
        ContentValues contentValues = new ContentValues();
        contentValues.put("mail", user.getMail());
        contentValues.put("password", user.getPassword());
        contentValues.put("name", user.getName());
        contentValues.put("phone", user.getPhone());
        contentValues.put("address", user.getAddress());


        long result = db.insert("User",null, contentValues);
        Log.i("insert", String.valueOf(result));
        //sql = "INSERT INTO User  (ID , mail, password, discriminator, name, Phone, Address ) VALUES( ?, ?, ?, ?, ?, ?, ?)";
        //db.rawQuery(sql, new String[]{Integer.toString(id), user.getMail(), user.getPassword(), user.getDiscriminator(), user.getName(), user.getPhone(), user.getAddress()});
        db.close();

        Log.i("PATHHHH", DB_PATH);
    }
    public String[] retriveUser(String id){
        String[] ret = {"","","","","",""};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String sql = "SELECT * FROM User WHERE ID = ? ";
        c = db.rawQuery(sql ,new String[]{id} );
        if (c.isAfterLast()){
            db.close();
            return ret;
        }
        c.moveToFirst();
        //String sid = c.getString(c.getColumnIndex("mail"));
        ret[0] = c.getString(1);
        ret[1] = c.getString(2);
        ret[2] = c.getString(4);
        ret[3] = c.getString(5);
        ret[4] = c.getString(6);

        return ret;
    }
    public int update(String[] info) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE User SET name = ? , "
                + "mail = ? , " + "Address = ? ," + "Phone = ? , "+ "password = ? "
                + "WHERE ID = ?";
        Cursor c;
        int n ;
        //c = db.rawQuery(sql ,new String[]{info[2],info[0], info[4], info[3], info[1],info[5]} );
        ContentValues cv = new ContentValues();
        cv.put("name" , info[2] );
        cv.put("mail" , info[0] );
        cv.put("Address" , info[4] );
        cv.put("Phone" , info[3] );
        cv.put("password" , info[1] );
        cv.put("ID" , info[5] );
        n = db.update("User", cv,"ID =" +info[5], null);
        Log.d("MAriam",info[2]+info[0]+ info[4]+ info[3]+ info[1]+info[5]);
        //Log.d("Mariam", c.getString(0));
        /*if (c.isAfterLast()){
            db.close();
            return n;
        }*/
        db.close();
        return n;
    }
    public List<Restaurant> getAllRestraunts(){
        Log.d("Nagham","get all");
        List<Restaurant> temp = new ArrayList<Restaurant>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        boolean kidsMenu = true;
        try {
            c = db.rawQuery("SELECT * FROM restaurant", null);
            if(c==null) return null;
            c.moveToFirst();
            do{
                if (c.getString(4).equals("0")) kidsMenu=false;
                Log.d("Nagham", c.getString(1));
                Restaurant r = new Restaurant(c.getString(0),c.getString(1),c.getString(2),c.getString(3),kidsMenu);
                temp.add(r);
            }
            while (c.moveToNext());{
                c.close();
            }
            return temp;
        }
        catch (Exception ex){}
        return temp;
    }
    public Restaurant get_one_restaurant(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        boolean kidsMenu = true;
        c = db.rawQuery("SELECT * FROM restaurant WHERE ID ="+id,null );
        Cursor c1;
        ArrayList<item> menu = new ArrayList<item>();
        c1 = db.rawQuery("SELECT * FROM Item WHERE resID = "+id,null);
        if(c1!=null){
            c1.moveToFirst();
            do {
            item i;
            item menu_item = new item(c1.getString(0),c1.getString(1),c1.getString(2),c1.getFloat(3));
            menu.add(menu_item);
            }
            while (c1.moveToNext());
            c1.close();
        }
        c1 = db.rawQuery("SELECT Address FROM branch WHERE resID = "+id,null);
        String Locations="";
        if(c1 != null) {
            c1.moveToFirst();
            do {
                Locations += c1.getString(0) + " , ";
            }
            while (c1.moveToNext());
        }
        if(Locations.length()>0)
            Locations=Locations.substring(0,Locations.length()-2);
        Log.i("ooooo",Locations);
        if(c==null)
        {
            return null;
        }

        if (c.isAfterLast())
        {
            db.close();
        }
        c.moveToFirst();
        if (c.getString(4).equals("0")) kidsMenu=false;
        Restaurant r= new Restaurant( c.getString(0),c.getString(1),c.getString(2),c.getString(3),kidsMenu,menu);
        c.close();
        return r;
    }

    public boolean checkMeal(String meal, String restID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String sql = "SELECT * FROM Item WHERE resID = ? AND name = ? ";
        c = db.rawQuery(sql ,new String[]{restID, meal} );
        Log.d("IN",restID+", " +meal);

        if (c.isAfterLast()){
            db.close();
            return false;
        }
        else return true;
    }

    public double calcPrice(String meal, String restID){
        float res = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String sql = "SELECT * FROM Item WHERE resID = ? AND name = ? ";
        c = db.rawQuery(sql ,new String[]{restID, meal} );
        if (c.isAfterLast()){
            db.close();
            return res;
        }
        c.moveToFirst();
        res = c.getFloat(3) ;
        Log.d("PRICE", Float.toString(res));

        return res;

    }

    public int insertOrder(Order order)
    {
        int id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", order.getTime());
        contentValues.put("delivery", order.isDelivery());
        contentValues.put("price", order.getPrice());
        contentValues.put("resID",order.getRestaurant_id());
        contentValues.put("timeDelivered",order.getTime_delivered());
        contentValues.put("done", order.isDone());
        contentValues.put("userID", order.getCusId());
        contentValues.put("items", order.getMeals());
        contentValues.put("numOfOrders", order.getNumberOfMeals());
        long result = db.insert("Orders",null, contentValues);
        Log.d("Nagham " , String.valueOf(result));
       return (int)result;

    }

    public String deleteOrder(String userID, String resID)
    {
        int x =-1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c ;
        String sql = "SELECT * FROM Orders WHERE userID = ? and resID = ? ";
        c = db.rawQuery(sql ,new String[]{userID, resID} );
        if (c.isAfterLast()){
            db.close();
            return "We couldn't found the order you made";
        }
        x  = db.delete("Orders","resID = ? and userID = ? and done = ?",new String[] {resID,userID,"0"});
        Log.d("_______________" ,"___________________________________________________________");
        Log.d("x", Integer.toString(x));
        if(x == 0) return "you can not delete the order now, it's on its way.";
        else return "Order deleted.";


    }

    public String updateData (Order order,String resID,String userID)
    {
        int x = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c ;
        String sql = "SELECT * FROM Orders WHERE userID = ? and resID = ? ";
        c = db.rawQuery(sql ,new String[]{userID, resID} );
        if (c.isAfterLast()){
            db.close();
            return "We couldn't found the order you made";
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", order.getTime());
        contentValues.put("delivery", order.isDelivery());
        contentValues.put("price", order.getPrice());
        contentValues.put("resID",order.getRestaurant_id());
        contentValues.put("timeDelivered",order.getTime_delivered());
        contentValues.put("done", order.isDone());
        contentValues.put("userID", order.getCusId());
        contentValues.put("items", order.getMeals());
        contentValues.put("numOfOrders", order.getNumberOfMeals());
        x = db.update("Orders", contentValues, "resID = ? and userID = ? and done = ?",new String[] {resID,userID,"0"});
        Log.d("__________________" ,"_________________________________________________________-");
        Log.d("x", Integer.toString(x));
        if(x==0) return "You can not update the order now, it's on its way.";
        return "Order Updated.";
    }
    public boolean reserve (Reservation reservation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("numOfPeople", reservation.getNoOfPeople());
        c.put("timeReserved", reservation.getTimeReserved());
        c.put("timeMade", reservation.getTimeMade());
        long result = db.insert("reservation",null, c);
        if (result==-1)
            return true;
        else
            return false;

    }

    public void updateReservation (Reservation reservation, String ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("numOfPeople", reservation.getNoOfPeople());
        c.put("timeReserved", reservation.getTimeReserved());
        db.update("Reservation", c,"id=?",new String[] {ID});
    }

    public void deleteReservation(String ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Reservation","id=?",new String[] {ID});
        db.close();

    }

 public boolean insertCompliant(String compliant, String bID, String rID, String cID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("branchID", bID);
        contentValues.put("resID", rID);
        contentValues.put("customer_ID", cID);
        contentValues.put("file", compliant);
        long result = db.insert("complian",null, contentValues);
        if (result==-1)
            return false;
        else
            return true;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}