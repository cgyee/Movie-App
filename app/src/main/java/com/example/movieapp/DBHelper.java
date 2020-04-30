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
    final static private String USERS_COLUMN_OBJ = "user_object";
    final static private String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + USERS_TABLE_NAME + "("+
                    USER_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    USERS_COLUMN_EMAIL + " TEXT," +
                    USERS_COLUMN_PASSWORD + " TEXT," +
                    USERS_COLUMN_OBJ + " BLOB" + ")";
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
    /**
     *Returns whether the String email does not already exists in the database
     * @param email a String to check against the database to see if the string exists
     * @return  if String email does not exists in database
     */
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

    /**
     *Return whether the String email and String Password are valid in the database
     * @param email String used to validate email in database table
     * @param password String used to validate password in database table
     * @return If String email and String password is valid in the database
     */
    public  Boolean validPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                USERS_TABLE_NAME,
                new String[] {USERS_COLUMN_EMAIL, USERS_COLUMN_PASSWORD},
                USERS_COLUMN_EMAIL +" = ?"+" AND "+ USERS_COLUMN_PASSWORD + " = ?",
                new String[] { email, password},
                null, null, null);

        int result = cursor.getCount();
        cursor.close();
        return result !=0 ? true:false;
    }

    /**
     *Inserts a new row into the database with the respective email and password column from the String email
     * and String password
     * @param email String to insert into email column of the database
     * @param password String to insert into password column of the database
     */
    public void insertNewUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_EMAIL, email);
        values.put(USERS_COLUMN_PASSWORD, password);
        sqLiteDatabase.insert(USERS_TABLE_NAME, null, values);
    }

    /**
     *Updates the database user_object column with a Serialized User object where specified by the email
     * @param user Serialized User object
     * @param email String email to associate with the User object in database
     */
    public void updateFavorites(byte[] user, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_OBJ, user );
        sqLiteDatabase.update(
                USERS_TABLE_NAME,
                values,
                USERS_COLUMN_EMAIL + " = ? ",
                new String[] {email});
    }

    /**
     * Retrieves the user object associated with the String email in the database
     * @param email String email to associate with the User object in database
     * @return Serialized User object
     */
    public byte [] getFavorites(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                USERS_TABLE_NAME,
                new String [] {USERS_COLUMN_OBJ},
                USERS_COLUMN_EMAIL +" = ? ",
                new String [] {email},
                null, null,null);

        cursor.moveToFirst();
        byte [] result = cursor.getBlob(0);
        return result;
    }

    /**
     * Updates the password in the database at the specified String oldEmail, String oldPassword,
     * with String newEmail, String newPassword
     * @param oldEmail String used to validate email in database table
     * @param oldPassword String used to validate password in database table
     * @param newEmail String used to update email in database table
     * @param newPassword String used to update email in database table
     */
    public void updatePassword(String oldEmail, String oldPassword, String newEmail, String newPassword) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_EMAIL, newEmail);
        values.put(USERS_COLUMN_PASSWORD, newPassword);
        sqLiteDatabase.update(
                USERS_TABLE_NAME,
                values,
                USERS_COLUMN_EMAIL +" = ?" + " AND " + USERS_COLUMN_PASSWORD + " = ?",
                new String[] {oldEmail, oldPassword});
    }

    /**
     * Updates the password in the database at the specified String oldEmail, String oldPassword,
     * with String newEmail, String newPassword
     * @param oldEmail String used to validate email in database table
     * @param oldPassword String used to validate password in database table
     * @param newEmail String used to update email in database table
     * @param newPassword String used to update email in database table
     */
    public void updateAccount(String oldEmail, String oldPassword, String newEmail, String newPassword) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_EMAIL, newEmail);
        values.put(USERS_COLUMN_PASSWORD, newPassword);
        sqLiteDatabase.update(
                USERS_TABLE_NAME,
                values,
                USERS_COLUMN_EMAIL +" = ?" + " AND " + USERS_COLUMN_PASSWORD + " = ?",
                new String[] {oldEmail, oldPassword});
    }

    /**
     * Delete the row associated the the String email and String password in the database
     * @param email String used to validate email in database table
     * @param password String used to passowrd email in database table
     */
    public void deleteAccount(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(USERS_TABLE_NAME,
                USERS_COLUMN_EMAIL +  " = ?" + " AND " +
                USERS_COLUMN_PASSWORD + " = ?",
                new String[] {email, password});
    }
}
