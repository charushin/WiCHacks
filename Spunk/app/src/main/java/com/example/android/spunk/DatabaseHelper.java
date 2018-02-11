package com.example.android.spunk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by swati on 2/10/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.
    private static String DB_PATH = "";
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static String DB_NAME = "SpunkData.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME).toString();
        //DB_PATH = "/data/data/"+myContext.getPackageName()+"/databases/";
    }



    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            //do nothing - database already exist
            System.out.println("----------Exists-----");
        }
        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
       /* File dbFile = new File(DB_PATH);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();*/
        boolean checkDB = false;
        try
        {
            String myPath = DB_PATH;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        String outFileName = DB_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public void db_delete()
    {
        File file = new File(DB_PATH);
        if(file.exists())
        {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    public boolean openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase != null;
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");
            File file = new File(DB_PATH);
            if(file.exists())
            {
                file.delete();
                System.out.println("delete database file.");
            }
        }
    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public String getPost(int postId){
        myDataBase = this.getReadableDatabase();
        String query = "SELECT title FROM posts WHERE _id = " + postId;
        System.out.println(query);
        Cursor c = myDataBase.rawQuery(query, null);
        String result = null;
        if(c != null && c.moveToFirst()){
            result = c.getString(0);
            c.close();
        }
        return result;
    }

    public ArrayList<String> getPostTitles(String type){
        ArrayList<String> postTitleList = new ArrayList<>();
        myDataBase = this.getReadableDatabase();
        String query = "SELECT title FROM posts where type = '"+type+"'";
        Cursor c = myDataBase.rawQuery(query, null);
        if(c != null ){
            if(c.moveToFirst()) {
                do {
                    String title = c.getString(c.getColumnIndex("title"));
                    System.out.println(title);
                    postTitleList.add(title);
                } while (c.moveToNext());
            }
        }
        else{
            return null;
        }
        return postTitleList;
    }

    public void setPost(String type, String title, String description, int userId){
        try {
            myDataBase = this.getReadableDatabase();
            ContentValues cv = new ContentValues(3);
            cv.put("type", type);
            cv.put("title", title);
            cv.put("description", description);
            cv.put("userId", userId);
            myDataBase.insert("Posts", null, cv);
            myDataBase.close();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
     /*   String query = "insert into Posts(TYPE,TITLE, DESCRIPTION ,userId) VALUES('"+type+"','"+title+"','"+description+"',"+userId+");";
     //   String q2 = "Select title from Posts where user_id = 1";
        try{
            Log.d(TAG,myDataBase.toString()+" "+myDataBase.getPath());
            myDataBase.execSQL(query);
         //   Cursor s = myDataBase.rawQuery(q2, null);
         //   Log.d(TAG, s.getString(0));
          //  s.close();
           // myDataBase.insert("Posts")
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }*/
    }

  /*  public ArrayList<PostEntity> getContents(int id){
        ArrayList<PostEntity> contents=new ArrayList<PostEntity>();
        String query="SELECT * FROM TRANSLATION WHERE PARENT_ID = " + id + " AND IS_DETAIL='NO'";
        System.out.println(query);
        Cursor c=myDataBase.rawQuery(query,null);
        System.out.println(c.getCount());
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {

                    int _id=c.getInt(c.getColumnIndex("_id"));
                    String englishText=c.getString(c.getColumnIndex("ENGLISH_TEXT"));
                    String karenText=c.getString(c.getColumnIndex("KAREN_TEXT"));
                    int parentId=c.getInt(c.getColumnIndex("PARENT_ID"));
                    String isParent=c.getString(c.getColumnIndex("IS_PARENT"));
                    String keywords1=c.getString(c.getColumnIndex("KEYWORD1"));
                    String keywords2=c.getString(c.getColumnIndex("KEYWORD2"));
               //     PostEntity PostEntity=new PostEntity(_id,englishText,karenText,parentId,isParent,keywords1,keywords2);
                    contents.add(PostEntity);
                }while (c.moveToNext());
            }
            else {
            //    PostEntity PostEntity=new PostEntity(-1,"No data here","No data here",0,"NO","","");
                contents.add(PostEntity);
            }
        }
        return contents;
    }*/

    public boolean checkIfParent(int id){
        String query="SELECT * FROM TRANSLATION WHERE PARENT_ID = "+id+" AND IS_PARENT = 'NO' AND IS_DETAIL = 'NO'" ;
        System.out.println(query);
        Cursor c=myDataBase.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            System.out.println("Record exists who are not parents");
            return true;
        }
        return false;
    }

    public int getId(String text){
        int id=0;
        String query="SELECT _id FROM TRANSLATION WHERE ENGLISH_TEXT='"+text+"'";
        Cursor c=myDataBase.rawQuery(query,null);
        if(c != null && c.moveToFirst()){
            id = c.getInt(c.getColumnIndex("_id"));
            c.close();
        }

        return id;
    }

  /*  public PostEntity getData(int id){
        PostEntity PostEntity=null;
        String query="SELECT * FROM TRANSLATION WHERE _id = "+id;
        Cursor c=myDataBase.rawQuery(query,null);
        if(c != null && c.moveToFirst()){
            int _id=c.getInt(c.getColumnIndex("_id"));
            String englishText=c.getString(c.getColumnIndex("ENGLISH_TEXT"));
            String karenText=c.getString(c.getColumnIndex("KAREN_TEXT"));
            int parentId=c.getInt(c.getColumnIndex("PARENT_ID"));
            String isParent=c.getString(c.getColumnIndex("IS_PARENT"));
            String keywords1=c.getString(c.getColumnIndex("KEYWORD1"));
            String keywords2=c.getString(c.getColumnIndex("KEYWORD2"));
            PostEntity=new PostEntity(_id,englishText,karenText,parentId,isParent,keywords1,keywords2);
            c.close();
        }
        return PostEntity;
    }
    public PostEntity getDetails(int id){
        PostEntity PostEntity=null;
        String query="SELECT * FROM TRANSLATION WHERE PARENT_ID = "+id+" AND IS_DETAIL='YES'";
        Cursor c=myDataBase.rawQuery(query,null);
        if(c != null && c.moveToFirst()){
            System.out.println("Got data for this");
            int _id=c.getInt(c.getColumnIndex("_id"));
            String englishText=c.getString(c.getColumnIndex("ENGLISH_TEXT"));
            String karenText=c.getString(c.getColumnIndex("KAREN_TEXT"));
            int parentId=c.getInt(c.getColumnIndex("PARENT_ID"));
            String isParent=c.getString(c.getColumnIndex("IS_PARENT"));
            String keywords1=c.getString(c.getColumnIndex("KEYWORD1"));
            String keywords2=c.getString(c.getColumnIndex("KEYWORD2"));
            PostEntity=new PostEntity(_id,englishText,karenText,parentId,isParent,keywords1,keywords2);
            c.close();
        }
        return PostEntity;
    }*/

    //Check which tables exist
    public void checkTables(){
        Cursor c = myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println("--------"+c.getString(0));
                Toast.makeText(myContext, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                c.moveToNext();
            }
        }
    }
}
