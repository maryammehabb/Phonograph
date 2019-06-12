package com.example.android.connection;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<User> lstUser = new ArrayList<User>();
    DbHelper dbHelper;
    Button btnGetData;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = (Button)findViewById(R.id.btnGetData);
        container = (LinearLayout) findViewById(R.id.container);

        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDatabase();

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstUser = dbHelper.getAllUsers();
                for (User user:lstUser){
                    LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = inflater.inflate(R.layout.row, null);
                    TextView txtid = (TextView)addView.findViewById(R.id.txtid);
                    TextView txtmail = (TextView)addView.findViewById(R.id.txtmail);

                    txtid.setText(user.getID());
                    txtmail.setText(user.getMail());

                    container.addView(addView);
                }
            }
        });

    }
}
