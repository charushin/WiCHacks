package com.example.android.spunk;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    DatabaseHelper myDBHelper;
    ArrayList<Comments> comments=new ArrayList<Comments>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int postId=this.getIntent().getExtras().getInt("postId");
        String title=this.getIntent().getExtras().getString("title");
        String desc=this.getIntent().getExtras().getString("description");

        initialiseDatabase();
        comments=myDBHelper.getComments(postId);

        final DetailAdapter adapter = new DetailAdapter(this, comments, R.layout.list_comments);

        // Create list view
        mListView = (ListView) findViewById(R.id.menu_list_view);

        mListView.setAdapter(adapter);

        //when an item is clicked
        final ArrayList<Comments> finalContents = comments;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comments comments = finalContents.get(position);
                System.out.println(comments.getDescription());
                //Intent detailIntent = null;
                /*if (postEntity.getPostID() == -1) {
                    System.out.println("No view to show now");
                    //see if popup can be displayed
                } else {
                    // boolean showDetailView = myDBHelper.checkIfParent(healthAppData._id);

                    // System.out.println(finalContents.hasSubMenu+"value of hasSubMenu");

                    Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                    detailIntent.putExtra("postId",postEntity.getPostID());
                    detailIntent.putExtra("title", postEntity.getTitle());
                    detailIntent.putExtra("title", postEntity.getDescription());
                    startActivity(detailIntent);
                }*/
            }

        });

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