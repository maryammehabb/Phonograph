package com.example.android.integration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.Integration.R;

public class fisrt extends AppCompatActivity {

    Button btnLoginF;
    Button btnSignUpF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt);


        btnLoginF = (Button) findViewById(R.id.btnLoginF);
        btnSignUpF = (Button) findViewById(R.id.btnSignUpF);

        btnLoginF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fisrt.this, Login.class);
                startActivity(intent);
            }
        });

        btnSignUpF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fisrt.this, signUp.class);
                startActivity(intent);
            }
        });

    }
}
