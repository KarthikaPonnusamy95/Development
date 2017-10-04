package com.example.abcde;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Declare Variables
	private static final String DB_NAME = "MyNotes";
	public static final String TABLE_NAME = "tablenotes";
	public static final String FIRSTNAME = "fName";
	private static final String LASTNAME = "lname";
	private static final String DOB = "dob";
	private static final String MOBILE = "mobile";
	private static final String EMAIL = "email";
	private static final String ADDRESS = "address";
	private static final String KEY_IMAGE = "image";

	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, version);
	}

	@Override	
	public void onCreate(SQLiteDatabase db) {
		
		// Create a database table
		String createQuery = "CREATE TABLE " + TABLE_NAME
				+ " (_id integer primary key autoincrement,"  + FIRSTNAME + ", "
				+ LASTNAME + "," + DOB + "," + MOBILE + "," + EMAIL + "," + ADDRESS +");";
		db.execSQL(createQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Database will be wipe on version change
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}