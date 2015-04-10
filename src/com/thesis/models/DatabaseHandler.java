package com.thesis.models;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "serviceManager";
 
    // Services table name
    private static final String TABLE_SERVICEMODEL = "service";
 
    // Services Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY = "key";
    private static final String URL = "url";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SERVICES_TABLE = "CREATE TABLE " + TABLE_SERVICEMODEL + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY + " TEXT,"
                + URL + " TEXT" + ")";
        db.execSQL(CREATE_SERVICES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICEMODEL);
 
        // Create tables again
        onCreate(db);
    }
   
    // Adding new service
    public void addService(ServiceModel service) {
    SQLiteDatabase db = this.getWritableDatabase();
 
    ContentValues values = new ContentValues();
    values.put(KEY, service.getKey()); 
    values.put(URL, service.getUrl()); 
 
    // Inserting Row
    db.insert(TABLE_SERVICEMODEL, null, values);
    db.close(); // Closing database connection
}
 // Getting single service
    ServiceModel getService(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_SERVICEMODEL, new String[] { KEY_ID,
                KEY, URL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        ServiceModel service = new ServiceModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
    
        return service;
    }
    public String getUrlById(String Key)
    {
    	String selectQuery = "SELECT  URL FROM " + TABLE_SERVICEMODEL+" WHERE KEY="+"'"+Key+"'";
    	String url="";
    	 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	url=cursor.getString(0);
            }while (cursor.moveToNext());
        }
		return url;
    	
    }
    // Getting All services
    public List<ServiceModel> getAllservices() {
        List<ServiceModel> serviceList = new ArrayList<ServiceModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICEMODEL;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	ServiceModel service = new ServiceModel();
                service.setID(Integer.parseInt(cursor.getString(0)));
                service.setKey(cursor.getString(1));
                service.setUrl(cursor.getString(2));
                // Adding service to list
                serviceList.add(service);
            } while (cursor.moveToNext());
        }
 
        // return service list
        return serviceList;
    }
 
    // Updating single service
    public int updateService(ServiceModel service) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY, service.getKey());
        values.put(URL, service.getUrl());
 
        // updating row
        return db.update(TABLE_SERVICEMODEL, values, KEY_ID + " = ?",
                new String[] { String.valueOf(service.getID()) });
    }
 
    // Deleting single service
    public void deleteService(ServiceModel service) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SERVICEMODEL, KEY_ID + " = ?",
                new String[] { String.valueOf(service.getID()) });
        db.close();
    }
 
 
    // Getting services Count
    public int getServicesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SERVICEMODEL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
}
