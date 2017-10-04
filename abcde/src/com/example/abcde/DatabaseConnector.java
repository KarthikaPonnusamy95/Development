package com.example.abcde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnector {

	// Declare Variables
	private static final String DB_NAME = "MyNotes";
	private static final String TABLE_NAME = "tablenotes";
	private static final String FIRSTNAME = "fName";
	private static final String LASTNAME = "lname";
	private static final String DOB = "dob";
	private static final String MOBILE = "mobile";
	private static final String EMAIL = "email";
	private static final String ADDRESS = "address";
	private static final String ID = "_id";
	private String mImageId;
	
	
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase database;
	//private SQLiteDatabase database1=null;
	private DatabaseHelper dbOpenHelper;
	public byte[] _image;
	public CharSequence _fName;

	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseHelper(context, DB_NAME, null,
				DATABASE_VERSION);

	}

	// Open Database function
	public void open() throws SQLException {
		// Allow database to be in writable mode
		database = dbOpenHelper.getWritableDatabase();
	}

	// Close Database function
	public void close() {
		if (database != null)
			database.close();
	}

	// Create Database function
	public void InsertNote(String fName, String lName,String dob, String mobile, String email, String address) {
		ContentValues newCon = new ContentValues();
		
		newCon.put(FIRSTNAME, fName);
		newCon.put(LASTNAME, lName);
		newCon.put(DOB, dob);
		newCon.put(MOBILE, mobile);

		newCon.put(EMAIL, email);
	
		newCon.put(ADDRESS, address);


		open();
		database.insert(TABLE_NAME, null, newCon);
		close();
	}

	// Update Database function
	public void UpdateNote(long id, String fName, String lName,String dob, String mobile, String email, String address) {
		ContentValues editCon = new ContentValues();
		
		
		editCon.put(FIRSTNAME, fName);
		editCon.put(LASTNAME, lName);
		editCon.put(DOB, dob);
		editCon.put(MOBILE, mobile);
		editCon.put(EMAIL, email);
		editCon.put(ADDRESS, address);

		open();
		database.update(TABLE_NAME, editCon, ID + "=" + id, null);
		close();
	}

	// Delete Database function
	public void DeleteNote(long id) {
		open();
		database.delete(TABLE_NAME, ID + "=" + id, null);
		close();
	}
	
	// List all data function
	public Cursor ListAllNotes() {
		return database.query(TABLE_NAME, new String[] { ID, FIRSTNAME }, null,
				null, null, null, FIRSTNAME);
	}
	
	

	// Capture single data by ID
	public Cursor GetOneNote(long id) {
		return database.query(TABLE_NAME, null, ID + "=" + id, null, null,
				null, null);
	}

}
