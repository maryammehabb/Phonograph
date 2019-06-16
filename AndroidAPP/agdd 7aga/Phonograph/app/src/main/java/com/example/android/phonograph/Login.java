package com.example.android.phonograph;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Login extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;
    dbHelper dbHelper;
    private TextView text;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        text = (TextView) findViewById(R.id.text);
        text.setText("");

        dbHelper = new dbHelper(getApplicationContext());
        //dbHelper.createDatabase();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userId = dbHelper.validate(Email.getText().toString(),Password.getText().toString());
                //dbHelper.del();
                try {
                    validate(Email.getText().toString(),
                            Password.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    private void validate(String userEmail, String userPassword) throws IOException {
        Log.i("nagham","validate");
        String data[];

        data = dbHelper.validateLogin(Email.getText().toString(),Password.getText().toString());
        //Information info = new Information(data[1], data[0], "" );
        Log.i("nagham","id is : "+data[0]);
        Log.i("nagham","name is : "+data[1]);
        //userId =Integer.parseInt(dbHelper.validateLogin(Email.getText().toString(),Password.getText().toString()));
        //Log.i("nagham","value is : "+userId);
        if(!data[0].equals("-1")){
            Intent intent = new Intent(Login.this, resturauntList.class);
            intent.putExtra("info",data);
            startActivity(intent);
        }
        else{
            Toast.makeText(getBaseContext(), "The email or the password you entered is incorrect.\nTry again.", Toast.LENGTH_SHORT).show();
            //text.setText("The email or the password you entered is incorrect.\nTry again.");
        }
    }
}
