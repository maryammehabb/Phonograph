package com.example.android.integration;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.Integration.R;

public class press extends AppCompatActivity {

    Button btnn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press);

        btnn = (Button) findViewById(R.id.btnn);
        btnn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String[] info = intent.getStringArrayExtra("info");
                Intent intentt = new Intent(press.this, callPage.class);
                intentt.putExtra("info",info);
                startActivity(intentt);
            }
        });
    }
}
