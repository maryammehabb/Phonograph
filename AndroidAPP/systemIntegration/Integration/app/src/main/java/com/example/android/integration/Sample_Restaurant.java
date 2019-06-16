package com.example.android.integration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.Integration.R;

public class Sample_Restaurant extends AppCompatActivity {
    String[] data1 = {"", "",""};
    String[] data2 = {"", "", "","",""};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_restaurant);
        TextView Name = findViewById(R.id.Restaurant_name);
        ListView Location= findViewById(R.id.Restaurant_Branches);
        TextView startOfDay=(TextView)findViewById(R.id.Restaurant_StartTime);
        TextView EndOfDay=(TextView)findViewById(R.id.Restaurant_endTime);
        Intent intent = getIntent();
        data1 = intent.getStringArrayExtra("info");
        dbHelper db= new dbHelper(this);
        Restaurant r= db.get_one_restaurant(data1[2]);
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
    }

    public void clickMe(View view){
        Button bt = (Button) view ;
        //setContentView(R.layout.activity_call_page);
        Intent intent = new Intent(Sample_Restaurant.this, callPage.class);
        data2[0] = data1 [0];
        data2[1] = data1 [1];
        data2[2] = data1[2];
        data2[3] = (String) bt.getTag();
        intent.putExtra("info",data2);
        startActivity(intent) ;
    }
}
