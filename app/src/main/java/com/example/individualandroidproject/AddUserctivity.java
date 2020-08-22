package com.example.individualandroidproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class AddUserctivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextUsername,mTextPassword,mTextfirstname,mTextlastname,mTextaddress,mTextphoneno;
    Button mButtonAdduser,mButtonUpdateuser,mButtobViewUser;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_userctivity);

        //Add titles for the page
        setTitle("Add Users");

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.editText_username);
        mTextPassword = (EditText) findViewById(R.id.editText_password);
        mTextfirstname = (EditText) findViewById(R.id.editText_firstname);
        mTextlastname = (EditText) findViewById(R.id.editText_lastname);
        mTextaddress = (EditText) findViewById(R.id.editText_address);
        mTextphoneno = (EditText) findViewById(R.id.editText_phoneno);
        mButtonAdduser = (Button) findViewById(R.id.btn_adduser);
        mButtonUpdateuser = (Button) findViewById(R.id.btn_updateuser);
        mButtobViewUser = (Button) findViewById(R.id.btn_viewuser);

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

        AddData();
        viewAll();

        //goto update user page

        mButtonUpdateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //link login page -. register page
                Intent updateuserIntent = new Intent(AddUserctivity.this,UpdateUserActivity.class);
                startActivity(updateuserIntent);

            }
        });
    }

    //when Add user button clicked add data to the database
    public void AddData(){
        mButtonAdduser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //calling inserData method using instance datahelper class

                                String user = mTextUsername.getText().toString().trim();
                                String pwd =mTextPassword.getText().toString().trim();
                                String fname = mTextfirstname.getText().toString().trim();
                                String lname =  mTextlastname.getText().toString().trim();
                                String addre = mTextaddress.getText().toString().trim();
                                String phone = mTextphoneno.getText().toString().trim();

                        long val = db.addstaff(user,pwd,fname,lname,addre,phone);
                        if(val > 0) {
                            if(awesomeValidation.validate()) {
                                Toast.makeText(AddUserctivity.this, "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
                                Intent moveToLogin = new Intent(AddUserctivity.this, AddUserctivity.class);
                                startActivity(moveToLogin);
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "Validation Faild",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(AddUserctivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    public void viewAll(){
        mButtobViewUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllData();
                        if(res.getCount() == 0){
                            //show message
                            showMessage("Error","Nothing Found");
                            return;
                        }else{
                            StringBuffer buffer = new StringBuffer();
                            //get all the data one by one
                            while (res.moveToNext()){
                                buffer.append("IDnew :"+res.getString(0)+"\n");// index is 0
                                buffer.append("usernamenew :"+res.getString(1)+"\n");// index is 1
                                buffer.append("passwordnew :"+res.getString(2)+"\n");// index is 2
                                buffer.append("firstname :"+res.getString(3)+"\n\n");// index is 3
                                buffer.append("lastname :"+res.getString(4)+"\n\n");// index is 4
                                buffer.append("address :"+res.getString(5)+"\n\n");// index is 5
                                buffer.append("phoneno :"+res.getString(6)+"\n\n");// index is 6

                            }
                            //show all data
                            showMessage("Data",buffer.toString());
                        }
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
