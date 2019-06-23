package com.example.android.phonograph;



import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity implements DataListener {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etAddress;
    private EditText etPhone;

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvAddress;
    private TextView tvPhone;

    private Button btnSignUp;
    private  String data[]={"", ""};

    ServerDB serverDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        serverDB = new ServerDB(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
                if (validate())
                    signUp();
            }
        });


    }

    public boolean validate(){
        if (!etName.getText().toString().matches("[a-zA-z]+")){
            Toast.makeText(getBaseContext(), "Your name shouldn't contain numbers or special charachters.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!etEmail.getText().toString().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,3}$")){
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

    public void signUp (){
        User user = new User();
        user.setMail(etEmail.getText().toString());
        user.setAddress(etAddress.getText().toString());
        user.setName(etName.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setPassword(etPassword.getText().toString());
        serverDB.saveToServerUser(user);
    }

    @Override
    public void onDataReceived(String id){
        data[0]=id;
        Log.i("IDDDDDDDDDD",id);
        if(!data[0].equals("-1"))
        {
            data[1]=etName.getText().toString();
            Intent intent = new Intent(signUp.this, resturauntList.class);
            intent.putExtra("info",data);
            startActivity(intent);
        }
    }
    @Override
    public void onLoginReceived(String id,String name)
    {}

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

}