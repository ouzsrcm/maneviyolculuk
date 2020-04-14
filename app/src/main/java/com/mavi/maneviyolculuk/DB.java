package com.mavi.maneviyolculuk;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by oguzhan.saricam on 10.10.2014.
 */

public class DB extends SQLiteOpenHelper {

    private static final String VERITABANI = "SeyruSuluk";
    private static final int VERSION = 1;
    private static String DB_PATH = "/data/data/com.mavi.maneviyolculuk/databases/";
    private static String DB_NAME = "SeyruSuluk.obj";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DB(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException{
        //System.out.println("create database");
        boolean dbExist = checkDataBase();

        //dbExist=false;

        if(dbExist){
            //do nothing - database already exist
            System.out.println("database varmis");
        }else{
            //System.out.println("database yokmus");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {
                //System.out.println("create databse exception dustu");
                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase(){
        File dbFile = myContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        //System.out.println("copy database");
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

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

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

}
