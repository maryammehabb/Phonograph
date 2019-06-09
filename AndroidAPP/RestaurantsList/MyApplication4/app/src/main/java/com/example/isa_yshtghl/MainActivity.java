package com.example.isa_yshtghl;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Button CallButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantlistview);
     /*   View convertView;
        ViewGroup parent;
        View rowView = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView= inflater.inflate(R.layout.restaurantlistviewitems, parent, false);
        CallButton = rowView.findViewById(R.id.bt);
*/
        try {
            View_Restraunts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

      /*  CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, callPage.class);
                startActivity(intent);
            }
        });*/



    }
    public void clickMe(View view){
        Button bt=(Button)view;
       setContentView(R.layout.activity_call_page);
        TextView t= findViewById(R.id.Restraunt_name);
        t.setText((String) bt.getTag());
        // Toast.makeText(this, "Button "+bt.getText().toString(),Toast.LENGTH_LONG).show();
    }

    public void View_Restraunts() throws SQLException
    {
        final Context context=this;
        ListView lstview=(ListView)findViewById(R.id.listv);
      /*  lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(context, "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
            }
        });*/


        ArrayList<String> rests = new ArrayList<String>();
        DbHelper db= new DbHelper(this);
        db.createDatabase();
        rests= (ArrayList<String>) db.getAllRestraunts();
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.restaurantlistview,R.id.txt,rests);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }
    }

