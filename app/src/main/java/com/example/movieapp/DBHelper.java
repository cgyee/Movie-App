package com.example.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    final static private String DATABASE_NAME = "My.db";
    final static private String USERS_TABLE_NAME = "Users";
    final static private String USER_COLUMN_ID ="id";
    final static private String USERS_COLUMN_EMAIL = "email";
    final static private String USERS_COLUMN_PASSWORD = "password";
    final static private String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + USERS_TABLE_NAME + "("+
                    USER_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    USERS_COLUMN_EMAIL + " TEXT," +
                    USERS_COLUMN_PASSWORD + " TEXT" + ")";
    final static private String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + USERS_TABLE_NAME;

    public DBHelper(Context context) { super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public boolean validEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USERS_TABLE_NAME,
                new String [] {USERS_COLUMN_EMAIL},
                USERS_COLUMN_EMAIL+" = ?",
                new String[] {email},
                null,null,null );

        int result = cursor.getCount();
        cursor.close();
        return result == 0 ? true : false;
    }

    public  Boolean validPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USERS_TABLE_NAME,
                new String[] {USERS_COLUMN_EMAIL, USERS_COLUMN_PASSWORD},
                USERS_COLUMN_EMAIL +" = ?"+" AND "+ USERS_COLUMN_PASSWORD + " = ?",
                new String[] { email, password},
                null, null, null);
        int result = cursor.getCount();
        cursor.close();
        return result !=0 ? true:false;
    }

    public void insertNewUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_EMAIL, email);
        values.put(USERS_COLUMN_PASSWORD, password);
        sqLiteDatabase.insert(USERS_TABLE_NAME, null, values);
    }
}
