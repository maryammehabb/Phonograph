package com.example.android.integration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.Integration.R;

public class Sample_Restaurant extends AppCompatActivity {
    String[] data2 = {"", "", ""};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_restaurant);
        TextView Name = findViewById(R.id.Restaurant_name);
        TextView Location= findViewById(R.id.Restaurant_Location);
        TextView startOfDay=(TextView)findViewById(R.id.Restaurant_StartTime);
        TextView EndOfDay=(TextView)findViewById(R.id.Restaurant_endTime);
        Intent intent = getIntent();
        data2 = intent.getStringArrayExtra("info");
        dbHelper db= new dbHelper(this);
        Restaurant r= db.get_one_restaurant(data2[2]);
        Name.setText(r.name);
        Location.setText(r.Locations);
        startOfDay.setText(r.timeStart);
        EndOfDay.setText(r.timeEnd);
        final Context context=this;
        ListView lstview=(ListView)findViewById(R.id.MenuLSTView);
        MenuItemAdapter adapter = new MenuItemAdapter(this ,R.layout.sample_restaurant,R.id.txt,r.menu);
        lstview.setAdapter(adapter);
        intent.putExtra("info",data2);
    }

}
