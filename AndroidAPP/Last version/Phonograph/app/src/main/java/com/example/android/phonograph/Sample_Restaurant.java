package com.example.android.phonograph;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Sample_Restaurant extends AppCompatActivity implements DataListener {
    String[] data1 = {"", "",""};
    String[] data2 = {"", "", "","",""};
    ServerDB serverDB ;
    TextView Name ;
    ListView Location ;
    TextView startOfDay ;
    TextView EndOfDay ;
    Intent intent ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        data1 = intent.getStringArrayExtra("info");
        Log.i("SAMPLE", data1[1]);
        serverDB = new ServerDB(this);
        serverDB.get_one_restaurant(data1[2]);

    }

    public void clickMe(View view){
        ImageView bt = (ImageView) findViewById(R.id.call_button) ;
        Intent intent = new Intent(Sample_Restaurant.this, callPage.class);
        data2[0] = data1 [0];
        data2[1] = data1 [1];
        data2[2] = data1[2];
        data2[3] = (String) bt.getTag();
        intent.putExtra("info",data2);
        startActivity(intent) ;
    }

    @Override
    public void onDataReceived(String response) {

    }

    @Override
    public void onLoginReceived(String id, String name) {

    }

    @Override
    public String oncancelReservation(String resp) {
        return null;
    }

    @Override
    public Restaurant onOneRestaurant(Restaurant r) {
        setContentView(R.layout.sample_restaurant);
        Name = findViewById(R.id.Restaurant_name);
        Location= findViewById(R.id.Restaurant_Branches);
        startOfDay=(TextView)findViewById(R.id.Restaurant_StartTime);
        EndOfDay=(TextView)findViewById(R.id.Restaurant_endTime);
        Name.setText(r.name);
        startOfDay.setText(r.timeStart);
        EndOfDay.setText(r.timeEnd);
        final Context context=this;
        ListView lstview=(ListView)findViewById(R.id.MenuLSTView);
        MenuItemAdapter adapter = new MenuItemAdapter(this ,R.layout.sample_restaurant,R.id.txt,r.menu);
        BranchCallAdapter calladapter = new BranchCallAdapter(this, R.layout.sample_restaurant,R.id.txt,r.Branches);
        lstview.setAdapter(adapter);
        Location.setAdapter(calladapter);
        intent.putExtra("info",data2);
        return r;
    }

    @Override
    public ArrayList<Restaurant> onAllRestaurants(ArrayList<Restaurant> rest) {
        return null;
    }

    @Override
    public void onDBResponse(String response) {

    }

    @Override
    public void onUpdateInfo(String response) {

    }

    @Override
    public void onRetriveUser(User user) {

    }
}