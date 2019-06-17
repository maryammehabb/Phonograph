package com.example.android.phonograph;



import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

    private boolean validate = true;

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

        dbHelper = new dbHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate = validate();
                if (validate==true)
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
        else if (!etPassword.getText().toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,15}$")){
            Toast.makeText(getBaseContext(), "Password should contain at least one number and one letter", Toast.LENGTH_LONG).show();
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

