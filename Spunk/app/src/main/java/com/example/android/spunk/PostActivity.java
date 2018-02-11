package com.example.android.spunk;

import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.IOException;

public class PostActivity extends AppCompatActivity {
    DatabaseHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initialiseDatabase();
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnPost = (Button) findViewById(R.id.button_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HomeActivity.this,PostActivity.class));
                EditText title = (EditText) findViewById(R.id.editTopic);
                EditText desc = (EditText) findViewById(R.id.editDesc);
                RadioButton quesRadioButton = (RadioButton) findViewById(R.id.questionRadioButton);
                RadioButton blogRadioButton = (RadioButton) findViewById(R.id.blogRadioButton);
              //  if(quesRadioButton.ge)
              //  myDBHelper.setPost();
            }
        });

        Button btnDiscard = (Button) findViewById(R.id.button_discard);
        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HomeActivity.this,PostActivity.class));
                NavUtils.navigateUpFromSameTask(PostActivity.this);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));
    }

    public void initialiseDatabase(){
        myDBHelper = new DatabaseHelper(this);
        //myDBHelper = new DatabaseHelper(this);
        try{
            myDBHelper.createDataBase();
        }
        catch(IOException e){
            throw new Error("Unable to create database");
        }
        try{
            myDBHelper.openDataBase();
        }
        catch(SQLException e){
            throw e;
        }
    }
}
