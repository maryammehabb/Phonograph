package com.example.android.integration;


import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.Integration.R;

import java.io.IOException;

public class signUp extends AppCompatActivity {

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

    dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etPhone = (EditText)findViewById(R.id.etPhone);
        tvName = (TextView)findViewById(R.id.tvName);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        tvAddress = (TextView)findViewById(R.id.tvAddress);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        dbHelper = new dbHelper(getApplicationContext());
        //dbHelper.createDatabase();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();

            }
        });


    }

    public void signUp (){
        User user = new User();
        user.setMail(etEmail.getText().toString());
        user.setAddress(etAddress.getText().toString());
        user.setDiscriminator("Customer");
        user.setName(etName.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setPassword(etPassword.getText().toString());
        dbHelper.insertUser(user);
        String data[];
        data = dbHelper.validateLogin(etEmail.getText().toString(),etPassword.getText().toString());
        if(!data[0].equals("-1")){
            Intent intent = new Intent(signUp.this, resturauntList.class);
            intent.putExtra("info",data);
            startActivity(intent);
        }



    }
}

