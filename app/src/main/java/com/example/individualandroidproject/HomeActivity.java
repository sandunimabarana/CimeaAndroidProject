package com.example.individualandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;

public class HomeActivity extends AppCompatActivity {

    TextView mTextViewadduser,mTextViewaddapperal;
    DatabaseHelper db;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Add titles for the page
        setTitle("Home Page");

        db = new DatabaseHelper(this);

        mTextViewadduser = (TextView) findViewById(R.id.textview_addusers);
        mTextViewaddapperal = (TextView) findViewById(R.id.textview_addapperals);

        mTextViewadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addUserIntent = new Intent(HomeActivity.this,AddUserctivity.class);
                startActivity(addUserIntent);
            }
        });
        mTextViewaddapperal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addApperalIntent = new Intent(HomeActivity.this,AddApperalActivity.class);
                startActivity(addApperalIntent);
            }
        });
    }
}
