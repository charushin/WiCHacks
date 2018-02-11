package com.example.android.spunk;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.IOException;
import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = PostActivity.class.getSimpleName();
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
                Log.d(TAG, "Clicked Post");
                EditText title = (EditText) findViewById(R.id.editTopic);
                EditText desc = (EditText) findViewById(R.id.editDesc);
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String type = null;
                if(selectedId == 0)
                    type = "Q";
                else
                    type = "B";
              //  if(quesRadioButton.ge)

               // mCursor.requery();
                //mAdapter.notifyDataSetChanged();
              //  mText.setText(null);
              myDBHelper.setPost(type, title.getText().toString(), desc.getText().toString(), 1);
       /*         Log.d(TAG, "After Set Post");
              ArrayList<String> titles = myDBHelper.getPostTitles("B");
              String title2 = myDBHelper.getPost(1);
              Log.d(TAG, "After get Post Titles Post");
                Log.d(TAG, title2);
              if(titles == null)
                  Log.d(TAG, "NUll output");
              else {
                  for (int i=0;i<titles.size();i++)
                      Log.d(TAG, titles.get(i));
              }*/
                Intent upIntent = NavUtils.getParentActivityIntent(PostActivity.this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(upIntent);
                finish();
         //   NavUtils.navigateUpFromSameTask(PostActivity.this);
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
