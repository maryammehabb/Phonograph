package com.example.android.phonograph;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.phonograph.*;

import java.io.IOException;
import java.util.ArrayList;

public class editInformation extends AppCompatActivity implements DataListener {

    private EditText etMail;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etName;
    private EditText etAddress;
    private Button btnEdit;
    String[] data;
    ServerDB serverDB;

    public boolean validate(){
        if (!etName.getText().toString().matches("[a-zA-z]+")){
            Toast.makeText(getBaseContext(), "Your name shouldn't contain numbers or special charachters.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!etMail.getText().toString().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,3}$")){
            Toast.makeText(getBaseContext(), "Enter a correct email, please", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etPassword.getText().toString().matches("^.{4,}$")){
            Toast.makeText(getBaseContext(), "Password should be at least 4 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etPassword.getText().toString().matches("^.{4,15}$")){
            Toast.makeText(getBaseContext(), "Password should be at most 15 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etPassword.getText().toString().matches("^[a-zA-Z]\\w{3,14}$")){
            Toast.makeText(getBaseContext(), "Password must start with a letter, contains numbers, letters and underscore", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etPhone.getText().toString().matches("^(01)(5|1|2|0)(\\d){8}$")){
            Toast.makeText(getBaseContext(), "Please, enter a correct phone number.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        Intent intent = getIntent();
        final String[] info = intent.getStringArrayExtra("info");
        serverDB = new ServerDB(this);
        serverDB.getUserInfo(info[0]);


    }

    @Override
    public void onDataReceived(String response) {

    }

    @Override
    public void onLoginReceived(String id, String name) {

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
        Intent intentt = new Intent(editInformation.this, resturauntList.class);
        intentt.putExtra("info", data);
        startActivity(intentt);
                /*if(x==true) Log.d("UPDATE" , "true");
                else Log.d("UPDATE" , "false");*/
        //Log.d("HAA", Integer.toString(x));

    }

    @Override
    public void onRetriveUser(User user) {

        etMail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPhone = (EditText) findViewById(R.id.etPhone);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        //userInfo = db.retriveUser(info[0]);

        etName.setText(user.getName());
        etMail.setText(user.getMail());
        etPassword.setText(user.getPassword());
        etPhone.setText(user.getPhone());
        etAddress.setText(user.getAddress());


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                data = intent.getStringArrayExtra("info");
                User u = new User();
                u.setID(data[0]);
                u.setPassword(etPassword.getText().toString());
                u.setName(etName.getText().toString());
                u.setPhone(etPhone.getText().toString());
                u.setAddress(etAddress.getText().toString());
                u.setMail(etMail.getText().toString());
                //int x = db.update(userInfo);
                data[1] = u.getName();
                if (validate())
                serverDB.updateUserInfo(u);


            }


        });

    }
}
