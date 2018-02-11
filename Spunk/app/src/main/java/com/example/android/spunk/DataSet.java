package com.example.android.spunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charu on 2/11/2018.
 */

public class DataSet {

    static ArrayList<PostEntity> postEntities;
    static ArrayList<Comments> comments;
    static ArrayList<Users> users;


    public static ArrayList<PostEntity> getPostEntities() {
        return postEntities;
    }

    public static void setPostEntities(ArrayList<PostEntity> postEntities) {
        DataSet.postEntities = postEntities;
    }

    public static ArrayList<Comments> getComments() {
        return comments;
    }

    public static void setComments(ArrayList<Comments> comments) {
        DataSet.comments = comments;
    }

    public static ArrayList<Users> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<Users> users) {
        DataSet.users = users;
    }


    public static List<Users> initializeUsers(){
        users=new ArrayList<Users>();

        Users u1=new Users(1,"abc","abc@gmail.com","abc");
        Users u2=new Users(2,"xyz","xyz@gmail.com","xyz");
        users.add(u1);
        users.add(u2);



        return users;
    }

    public static List<PostEntity> initializePosts(){
        postEntities=new ArrayList<PostEntity>();

        PostEntity pe1=new PostEntity(1,"Q","Why am I being bullied?","Why am I being bullied?",1);
        PostEntity pe2=new PostEntity(2,"Q","What is bullying?","What is bullying?",1);
        postEntities.add(pe1);
        postEntities.add(pe2);
        return postEntities;
    }


    public static List<Comments> initializeComments(){
        comments=new ArrayList<Comments>();


        Comments c1=new Comments(1,2,1,"It's Okay");
        Comments c2=new Comments(2,1,1,"Thank you!");
        comments.add(c1);
        comments.add(c2);
        return comments;
    }
}
