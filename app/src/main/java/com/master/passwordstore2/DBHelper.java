package com.master.passwordstore2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public  class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME ="DBPASS";
    public static final String TABLENAME = "StoredPassword";
    public static final int DB_version = 1;

    List<String> appName = new ArrayList<>();
    List<String> passwords = new ArrayList<>();

    DBHelper(Context context){
        super(context,DBNAME,null,DB_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME +
                    "(APPNAME VARCHAR,PASSWORD VARCHAR)");

            System.out.println("Table Created Successfully");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Table Creation Failed");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS StoredPassword");

        this.onCreate(db);
    }


    void insert(String name , String pass){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("APPNAME",name);
            contentValues.put("PASSWORD",pass);

            db.insert("StoredPassword",null,contentValues);

            System.out.println("Insertion Successfully Completed");
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Insertion Exception");
        }

    }
    void deleteAllRows(){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("Delete from StoredPassword");
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception in DeleteAllRows");
        }
    }

    void delete(String name){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("StoredPassword","APPNAME = ?",new String[]{name});
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception in Delete Operation");
        }

    }

    void update(String oldname,String newname,String newpass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("APPNAME",newname);
        contentValues.put("PASSWORD",newpass);
        db.update(TABLENAME,contentValues,"APPNAME = ?",new String[]{oldname});
        db.close();
    }

    void getData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLENAME,null);

        cursor.moveToFirst();

        int count = cursor.getCount();

        while(count>0){
            count--;
            this.appName.add(cursor.getString(cursor.getColumnIndex("APPNAME")));
            this.passwords.add(cursor.getString(cursor.getColumnIndex("PASSWORD")));

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }

    List<String> getAppName(){
        return this.appName;
    }

    List<String> getPasswords(){
        return this.passwords;
    }

}
