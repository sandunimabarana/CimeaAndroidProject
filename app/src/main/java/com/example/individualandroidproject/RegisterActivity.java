package com.example.individualandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextUsername,mTextPassword,mTextCnPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Add titles for the page
        setTitle("Registration");

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.editText_username);
        mTextPassword = (EditText) findViewById(R.id.editText_password);
        mTextCnPassword = (EditText) findViewById(R.id.editText_cnf_password);
        mButtonRegister = (Button) findViewById(R.id.btn_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Initialize Validation  Styles
        //For email
        awesomeValidation.addValidation(this,R.id.editText_username,
                 Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //For password
        awesomeValidation.addValidation(this,R.id.editText_password,
                ".{6,}",R.string.invalid_password);


        mTextViewLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(LoginIntent);
                    }
                }
        );
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnPassword.getText().toString().trim();

                if(pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd);
                    if(awesomeValidation.validate()) {
                        if (val > 0) {
                            Toast.makeText(RegisterActivity.this, "You have Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(moveToLogin);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Please Enter Unique Email", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Validation Faild",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this," Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}
