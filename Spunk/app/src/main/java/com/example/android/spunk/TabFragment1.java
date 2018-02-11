package com.example.android.spunk;

/**
 * Created by charushi on 2/10/18.
 */

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class TabFragment1 extends Fragment {
    DatabaseHelper myDBHelper;
    //ArrayList<PostEntity> contents=new ArrayList<PostEntity>();
    ArrayList<PostEntity> contents;
    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View v=inflater.inflate(R.layout.tab_fragment_1, container, false);


        //initialiseDatabase();
        //contents=myDBHelper.getPosts();
        //contents.add(new PostEntity(1,"Q","Title1","Desc1",1));
        //contents.add(new PostEntity(2,"Q","Title2","Desc2",1));
        contents=(ArrayList)DataSet.initializePosts();

        //System.out.println(dataArrayList);
        // Create adapter
        final DataAdapter adapter = new DataAdapter(getContext(), contents, R.layout.list_items);

        // Create list view
        mListView = (ListView) v.findViewById(R.id.menu_list_view);

        mListView.setAdapter(adapter);

        //when an item is clicked
        final ArrayList<PostEntity> finalContents = contents;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostEntity postEntity = finalContents.get(position);
                //Intent detailIntent = null;
                if (postEntity.get_id() == -1) {
                    System.out.println("No view to show now");
                    //see if popup can be displayed
                } else {
                   // boolean showDetailView = myDBHelper.checkIfParent(healthAppData._id);

                    // System.out.println(finalContents.hasSubMenu+"value of hasSubMenu");

                     Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                    detailIntent.putExtra("postId",postEntity.get_id());
                    detailIntent.putExtra("title", postEntity.getTitle());
                    detailIntent.putExtra("title", postEntity.getDescription());
                    startActivity(detailIntent);
                }
            }

        });







        //set up back button option for children activity


    return v;

    }



    public void initialiseDatabase(){
        myDBHelper = new DatabaseHelper(getContext());
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
