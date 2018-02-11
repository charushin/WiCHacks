package com.example.android.spunk;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by swati on a1/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.
    private static String DB_PATH = "";

    private static String DB_NAME = "translation";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
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
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            System.out.println("----------After Get-----");
            //this.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
//        SQLiteDatabase checkDB = null;
//        try{
//            String myPath = DB_PATH + DB_NAME;
//            System.out.println("-------------"+myPath);
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//        }catch(SQLiteException e){
//
//            //database does't exist yet.
//
//        }
//
//        if(checkDB != null){
//
//            checkDB.close();
//
//        }
//
//        return checkDB != null ? true : false;
        File dbFile = new File(DB_PATH);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        System.out.println("----------Inside Copy-----");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME+".sqlite");
        System.out.println("----------"+ myInput.toString());
        // Path to the just created empty db
        String outFileName = DB_PATH;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public boolean openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
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

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public String getEnglishText(int id){
      //  myDataBase = this.getReadableDatabase();
        String query = "SELECT ENGLISH_TEXT FROM TRANSLATION WHERE _id = " + id;
        System.out.println(query);
        Cursor c = myDataBase.rawQuery(query, null);
        String result = null;
        if(c != null && c.moveToFirst()){
            result = c.getString(0);
            c.close();
        }
        return result;
    }

    public String getKarenText(int id){
       // myDataBase = this.getReadableDatabase();
        String query = "SELECT KAREN_TEXT FROM TRANSLATION WHERE _id = " + id;;
        System.out.println(query);
        Cursor c = myDataBase.rawQuery(query, null);
        String result = null;
        if(c != null && c.moveToFirst()){
            result = c.getString(0);
            c.close();
        }
        return result;
    }

    /*public Map<String,String> getContents(int id){
        Map<String,String> contents=new HashMap<String, String>();
        String query="SELECT ENGLISH_TEXT, KAREN_TEXT FROM TRANSLATION WHERE PARENT_ID = "+id;
        System.out.println(query);
        Cursor c=myDataBase.rawQuery(query,null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    contents.put(c.getString(c.getColumnIndex("ENGLISH_TEXT")),c.getString(c.getColumnIndex("KAREN_TEXT")));
                }while (c.moveToNext());
            }
        }
        return contents;
    }*/
    public ArrayList<HealthAppData> getContents(int id){
        ArrayList<HealthAppData> contents=new ArrayList<HealthAppData>();
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
                    HealthAppData healthAppData=new HealthAppData(_id,englishText,karenText,parentId,isParent,keywords1,keywords2);
                    contents.add(healthAppData);
                }while (c.moveToNext());
            }
            else {
                HealthAppData healthAppData=new HealthAppData(-1,"No data here","No data here",0,"NO","","");
                contents.add(healthAppData);
            }
        }
        return contents;
    }

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

    public HealthAppData getData(int id){
        HealthAppData healthAppData=null;
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
            healthAppData=new HealthAppData(_id,englishText,karenText,parentId,isParent,keywords1,keywords2);
            c.close();
        }
        return healthAppData;
    }
    public HealthAppData getDetails(int id){
        HealthAppData healthAppData=null;
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
            healthAppData=new HealthAppData(_id,englishText,karenText,parentId,isParent,keywords1,keywords2);
            c.close();
        }
        return healthAppData;
    }

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