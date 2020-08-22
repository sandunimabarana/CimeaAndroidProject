package com.example.individualandroidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    //adduser table
    public static final String TABLE_NAME1 = "addstaff";


    //Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_PRICE = "price";
    private static final String KEY_POTO = "poto";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE addstaff (IDnew INTEGER PRIMARY KEY AUTOINCREMENT, usernamenew TEXT NOT NULL UNIQUE, passwordnew TEXT NOT NULL, firstname TEXT, lastname TEXT, address TEXT, phoneno TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS APPERAL (ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR NOT NULL, price VARCHAR NOT NULL, image BLOG NOT NULL )");

        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_FNAME +" TEXT,"
                + KEY_PRICE +" TEXT,"
                + KEY_POTO  +" BLOB" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    //Registration
    public long addUser(String user, String s1){
        SQLiteDatabase db = this.getWritableDatabase();
        s1 = md5(s1);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,user);//passing data to column NAME
        contentValues.put(COL_3,s1);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return res;

    }
    //login
    public boolean checkUser(String username, String s1){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        s1 = md5(s1);
        String selection = COL_2 + " = ?"+ " AND " + COL_3 + "=?";
        String[] selectionArgs = { username, s1 };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }else{
            return false;
        }

    }

    //Add staff members
    public long addstaff(String usernamenew,String passwordnew,String firstname,String lastname,String addressnew, String phoneno){
        //create instance of database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("usernamenew",usernamenew);//passing data to column NAME
        contentValues1.put("passwordnew",passwordnew);
        contentValues1.put("firstname",firstname);
        contentValues1.put("lastname",lastname);
        contentValues1.put("address",addressnew);
        contentValues1.put("phoneno",phoneno);
        long result = db.insert("addstaff",null,contentValues1);
        db.close();
        return result;
    }

    //Insert Apperal Data
    public void insertData(String name, String price, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO APPERAL VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);

        statement.executeInsert();
    }

    //Update user details
    public boolean updateData(String id, String usernamenew,String passwordnew,String firstname,String lastname,String addressnew, String phoneno){
        //create instance of database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDnew",id);
        contentValues.put("usernamenew",usernamenew);//passing data to column NAME
        contentValues.put("passwordnew",passwordnew);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("address",addressnew);
        contentValues.put("phoneno",phoneno);
        db.update(TABLE_NAME1,contentValues,"IDnew = ?",new String[] { id });
        return true;
    }
    //get all data
    public Cursor getAllData(){
        //create instance of database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1, null);
        return res;
    }

    //Insert values to the table contacts
    public void addContacts(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_PRICE, contact.getPrice());
        values.put(KEY_POTO, contact.getImage() );


        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    /**
     *Getting All Contacts
     **/

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFName(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setImage(cursor.getBlob(3));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    /**
     *Updating single contact
     **/

    public int updateContact(Contact contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_PRICE, contact.getPrice());
        values.put(KEY_POTO, contact.getImage());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     *Deleting single contact
     **/

    public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

}