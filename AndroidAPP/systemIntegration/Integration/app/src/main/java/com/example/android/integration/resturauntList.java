package com.example.android.integration;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.android.Integration.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class resturauntList extends AppCompatActivity {

    String[] data1 = {"", ""};
    String[] data2 = {"", "", ""};
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private NavigationView nav_view;
    private Spinner spinner1, spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturaunt_list);

        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.open , R.string.close);
        //abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        data1 = intent.getStringArrayExtra("info");



        nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.editProfile){
                    Intent i = new Intent(resturauntList.this, editInformation.class);
                    i.putExtra("info",data1);
                    startActivity(i);
                }
                if (id == R.id.logOut){
                    Intent i = new Intent(resturauntList.this, fisrt.class);
                    startActivity(i);
                }

                return true;
            }
        });



        try {
            View_Restraunts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void Show_click(View view)
    {
        Button show=(Button) view;
        Intent intent = new Intent(resturauntList.this, Sample_Restaurant.class);
        data2[0] = data1 [0];
        data2[1] = data1 [1];
        data2[2] = (String) show.getTag();
     //   addItemsOnSpinner2((String) show.getTag());
        Log.i("iiiiiiiiii", (String) show.getTag());
        Log.i("iiiiiiiiii",data2[2]);

        //Log.i("iiiiiiiiii", (String) show.getTag());
        //Log.i("iiiiiiiiii",data2[2]);
        intent.putExtra("info",data2);
        startActivity(intent) ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    public void View_Restraunts() throws SQLException
    {
        final Context context=this;
        ListView lstview=(ListView)findViewById(R.id.listv);
        ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
        dbHelper db= new dbHelper(this);
        db.createDatabase();
        rests= (ArrayList<Restaurant>) db.getAllRestraunts();
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.activity_resturaunt_list,R.id.txt,rests);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }
}
