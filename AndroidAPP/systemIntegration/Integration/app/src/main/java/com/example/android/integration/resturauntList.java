package com.example.android.integration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.Integration.R;

import java.sql.SQLException;
import java.util.ArrayList;

public class resturauntList extends AppCompatActivity {

    String[] data1 = {"", ""};
    String[] data2 = {"", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturaunt_list);
        Intent intent = getIntent();
        data1 = intent.getStringArrayExtra("info");
        try {
            View_Restraunts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clickMe(View view){
        Button bt = (Button) view ;
        //setContentView(R.layout.activity_call_page);
        Intent intent = new Intent(resturauntList.this, callPage.class);
        data2[0] = data1 [0];
        data2[1] = data1 [1];
        data2[2] = (String) bt.getTag();
        intent.putExtra("info",data2);
        startActivity(intent) ;
        //TextView t= findViewById(R.id.Restraunt_name);
        //t.setText((String) bt.getTag());
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
        dbHelper db= new dbHelper(this);
        db.createDatabase();
        rests= (ArrayList<String>) db.getAllRestraunts();
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.activity_resturaunt_list,R.id.txt,rests);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }
}
