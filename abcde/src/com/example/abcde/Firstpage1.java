package com.example.abcde;

import android.os.AsyncTask;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Firstpage1 extends ListActivity {

	// Declare Variables
	public static final String ROW_ID = "row_id";
	private static final String FIRSTNAME = "fName";
	private static final String LASTNAME = "lname";
	private static final String DOB = "dob";
	private static final String MOBILE = "mobile";
	private static final String EMAIL = "email";
	private static final String ADDRESS = "address";
	private ListView noteListView;
	private CursorAdapter noteAdapter;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_firstpage);

		// Locate ListView
		noteListView = getListView();

		// Prepare ListView Item Click Listener
		noteListView.setOnItemClickListener(viewNoteListener);

		// Map all the titles into the ViewTitleNotes TextView
		String[] from = new String[] { FIRSTNAME };
		int[] to = new int[] { R.id.ViewTitleNotes };

		// Create a SimpleCursorAdapter
		noteAdapter = new SimpleCursorAdapter(Firstpage1.this,
				R.layout.activity_main, null, from, to);

		// Set the Adapter into SimpleCursorAdapter
		setListAdapter(noteAdapter);
	}

	// Capture ListView item click
	OnItemClickListener viewNoteListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			// Open ViewNote activity
			//Intent viewnote = new Intent(Firstpage1.this, ContactDetails.class);

			// Pass the ROW_ID to ViewNote activity
			//viewnote.putExtra(ROW_ID, arg3);
			//startActivity(viewnote);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();

		// Execute GetNotes Asynctask on return to MainActivity
		new GetNotes().execute((Object[]) null);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onStop() {
		Cursor cursor = noteAdapter.getCursor();

		// Deactivates the Cursorl
		if (cursor != null)
			cursor.deactivate();

		noteAdapter.changeCursor(null);
		super.onStop();
	}

	// Create an options menu
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add("Add contacts").setOnMenuItemClickListener(this.Add).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//menu.getItem(0).setIcon(R.drawable.plus);

		menu.add("Groups").setOnMenuItemClickListener(this.Group).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//menu.getItem(1).setIcon(R.drawable.colleagues);

		menu.add("Search").setOnMenuItemClickListener(this.Search).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//menu.getItem(2).setIcon(R.drawable.search);
		

		menu.add("Day Calc").setOnMenuItemClickListener(this.Dayc).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}

	// private MenuItem setOnMenuItemClickListener(OnMenuItemClickListener add2)
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// Capture menu item click
	OnMenuItemClickListener Add = new OnMenuItemClickListener() {
		public boolean onMenuItemClick(MenuItem item) {

			// Open AddEditNotes activity
			Intent addnote = new Intent(Firstpage1.this, MainActivity.class);
			startActivity(addnote);

			return false;

		}
	};

	OnMenuItemClickListener Group = new OnMenuItemClickListener() {
		public boolean onMenuItemClick(MenuItem item) {

			// Open AddEditNotes activity
			//Intent addnote = new Intent(Firstpage1.this, Groups.class);
			//startActivity(addnote);

			return false;

		}
	};

	OnMenuItemClickListener Search = new OnMenuItemClickListener() {
		public boolean onMenuItemClick(MenuItem item) {

			// Open AddEditNotes activity
			//Intent addnote = new Intent(Firstpage1.this, Search.class);
			//startActivity(addnote);

			return false;

		}
	};
	OnMenuItemClickListener Dayc = new OnMenuItemClickListener() {
		public boolean onMenuItemClick(MenuItem item) {

			// Open AddEditNotes activity
			//Intent addnote = new Intent(Firstpage1.this, Day.class);
			//startActivity(addnote);

			return false;

		}
	};

	// GetNotes AsyncTask
	private class GetNotes extends AsyncTask<Object, Object, Cursor> {
		DatabaseConnector dbConnector = new DatabaseConnector(Firstpage1.this);

		@Override
		protected Cursor doInBackground(Object... params) {
			// Open the database
			dbConnector.open();

			return dbConnector.ListAllNotes();
		}

		@Override
		protected void onPostExecute(Cursor result) {
			noteAdapter.changeCursor(result);

			// Close Database
			dbConnector.close();
		}
	}
}
