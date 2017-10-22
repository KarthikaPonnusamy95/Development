package com.example.databaseexample;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
//import android.support.v7.AppCompatActivity;
import android.widget.ListView;

public class EmployeeActivity extends Activity
{

	
	List<Employee> empList;
	SQLiteDatabase sq_DB;
	ListView listViewEmp;
	EmployeeAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emp_listview);
		
		listViewEmp = (ListView) findViewById(R.id.empListView);
		empList = new ArrayList<>();
		sq_DB = openOrCreateDatabase(MainActivity.DateBaseName, MODE_PRIVATE,null);
		showEmployeesFromDatabase();
		
	}
	
	private void showEmployeesFromDatabase()

	{
		Cursor cursorEmp = sq_DB.rawQuery("SELECT * FROM emp", null);
		
		cursorEmp.moveToFirst();
		if (!cursorEmp.isAfterLast())
		{
			do
			{
				
				empList.add(new Employee(cursorEmp.getInt(cursorEmp.getColumnIndex("ID")), 
				cursorEmp.getString(cursorEmp.getColumnIndex("NAME")),
				cursorEmp.getString(cursorEmp.getColumnIndex("DEPT")),
				cursorEmp.getString(cursorEmp.getColumnIndex("SALARY")),
				cursorEmp.getString(cursorEmp.getColumnIndex("JOINDATE"))));

			} while (cursorEmp.moveToNext());
		}
		

		
		cursorEmp.close();
		adapter= new EmployeeAdapter(this, R.layout.emplayout, empList, sq_DB) ;
		listViewEmp.setAdapter(adapter);
	}
	
}
