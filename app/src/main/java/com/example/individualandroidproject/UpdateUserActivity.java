package com.example.individualandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class UpdateUserActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextId,mTextUsername,mTextPassword,mTextfirstname,mTextlastname,mTextaddress,mTextphoneno;
    Button mButtonUpdateuser;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        //Add titles for the page
        setTitle("Update Users");

        db = new DatabaseHelper(this);
        mTextId = (EditText) findViewById(R.id.editText_id);
        mTextUsername = (EditText) findViewById(R.id.editText_username);
        mTextPassword = (EditText) findViewById(R.id.editText_password);
        mTextfirstname = (EditText) findViewById(R.id.editText_firstname);
        mTextlastname = (EditText) findViewById(R.id.editText_lastname);
        mTextaddress = (EditText) findViewById(R.id.editText_address);
        mTextphoneno = (EditText) findViewById(R.id.editText_phoneno);
        mButtonUpdateuser = (Button) findViewById(R.id.btn_updateuser);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Initialize Validation  Styles
        //For email
        awesomeValidation.addValidation(this,R.id.editText_username,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //For password
        awesomeValidation.addValidation(this,R.id.editText_password,
                ".{6,}",R.string.invalid_password);

        //For First Name
        awesomeValidation.addValidation(this,R.id.editText_firstname,
                RegexTemplate.NOT_EMPTY,R.string.invalid_firstname);

        //For Last Name
        awesomeValidation.addValidation(this,R.id.editText_lastname,
                RegexTemplate.NOT_EMPTY,R.string.invalid_lastname);
        //For Last Name
        awesomeValidation.addValidation(this,R.id.editText_address,
                RegexTemplate.NOT_EMPTY,R.string.invalid_address);

        //For Phone No
        awesomeValidation.addValidation(this, R.id.editText_phoneno, "^[+]?[0-9]{10,13}$", R.string.invalid_phone);

        UpdateData();
    }
//Update user details
    public void UpdateData(){
        mButtonUpdateuser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = db.updateData(mTextId.getText().toString(),
                                mTextUsername.getText().toString(),
                                mTextPassword.getText().toString(),
                                mTextfirstname.getText().toString(),
                                mTextlastname.getText().toString(),
                                mTextaddress.getText().toString(),
                                mTextphoneno.getText().toString());

                        if( isUpdated == true){
                            if(awesomeValidation.validate()){
                            Toast.makeText(UpdateUserActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "Validation Faild",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(UpdateUserActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
