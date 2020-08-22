package com.example.individualandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername,mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Add titles for the page
        setTitle("Login");



        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.editText_username);
        mTextPassword = (EditText) findViewById(R.id.editText_password);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Initialize Validation  Styles
        //For email
        awesomeValidation.addValidation(this,R.id.editText_username,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //For password
        awesomeValidation.addValidation(this,R.id.editText_password,
                ".{6,}",R.string.invalid_password);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //link login page -. register page
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);

            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true){
                    if(awesomeValidation.validate()) {
                        Intent HomePage = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(HomePage);

                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Validation Faild",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"Please Input Valid Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
