package com.example.android.integration;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.Integration.R;

import java.io.IOException;

public class editInformation extends AppCompatActivity {

    private EditText etMail;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etName;
    private EditText etAddress;
    private Button btnEdit;
    dbHelper db;
    private TextView tvMail;
    private TextView tvPassword;
    private TextView tvPhone;
    private TextView tvName;
    private TextView tvAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        db = new dbHelper(getApplicationContext());
        db.createDatabase();

        Intent intent = getIntent();
        final String[] info = intent.getStringArrayExtra("info");

        tvMail = (TextView) findViewById(R.id.tvEmail);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPhone = (TextView) findViewById(R.id.tvPhone);

        etMail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPhone = (EditText) findViewById(R.id.etPhone);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        final String userInfo[];
        userInfo = db.retriveUser(info[0]);

        etName.setText(userInfo[2]);
        etMail.setText(userInfo[0]);
        etPassword.setText(userInfo[1]);
        etPhone.setText(userInfo[3]);
        etAddress.setText(userInfo[4]);

        userInfo[5] = info[0];

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo[1] = etPassword.getText().toString();
                userInfo[2] = etName.getText().toString();
                userInfo[3] = etPhone.getText().toString();
                userInfo[4] = etAddress.getText().toString();
                int x = db.update(userInfo);
                /*if(x==true) Log.d("UPDATE" , "true");
                else Log.d("UPDATE" , "false");*/
                Log.d("HAA" , Integer.toString(x));

            }
        });
    }
}
