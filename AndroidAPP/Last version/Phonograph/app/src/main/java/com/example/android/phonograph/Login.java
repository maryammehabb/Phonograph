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
import java.util.ArrayList;


public class Login extends AppCompatActivity implements DataListener {

    private EditText Email;
    private EditText Password;
    private Button Login;
    private TextView text;
    String data[]={"",""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        text = (TextView) findViewById(R.id.text);
        text.setText("");


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    validate(Email.getText().toString(),
                            Password.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    private void validate(String userEmail, String userPassword) throws IOException {

        ServerDB db= new ServerDB(this);
        db.check_login(Email.getText().toString(),Password.getText().toString());


    }
    @Override
    public void onLoginReceived(String id,String name){
        if(Integer.parseInt(id)>0)
        {
            data[0]=id;
            data[1]=name;
            Intent intent = new Intent(Login.this, resturauntList.class);
            intent.putExtra("info",data);
            startActivity(intent);
        }
        else if(Integer.parseInt(id)==-1)
        {
            Toast.makeText(getBaseContext(), "The email or the password you entered is incorrect.\nTry again.", Toast.LENGTH_SHORT).show();
            //text.setText("The email or the password you entered is incorrect.\nTry again.");
        }
        else
        {
            Toast.makeText(getBaseContext(), "There is Network ERROR", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public String oncancelReservation(String resp) {
        return null;
    }

    @Override
    public Restaurant onOneRestaurant(Restaurant rest) {
        return null;
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

    @Override
    public void onDataReceived(String id){}
}
