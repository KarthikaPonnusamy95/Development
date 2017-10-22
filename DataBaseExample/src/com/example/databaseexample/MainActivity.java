package com.example.databaseexample;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
//import android.support.v7.appcompat.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener  {

	public static final String DateBaseName = "EmployeeDatabase";
	
	//TextView txtViewEmp;
	EditText editTxtName,editTxtSalary;
	Spinner spinnerEmp;
	SQLiteDatabase sql_DB;
	Button btnSave,btnView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//txtViewEmp = (TextView) findViewById(R.id.textViewEmployees);
		editTxtName = (EditText) findViewById(R.id.editTextName);
		editTxtSalary = (EditText) findViewById(R.id.editTextSalary);
		spinnerEmp = (Spinner) findViewById(R.id.spDepartment);
		
		btnSave = (Button) findViewById(R.id.btnAdd);
		btnView = (Button) findViewById(R.id.btnView);
		//txtViewEmp.setOnClickListener(this);
		sql_DB = openOrCreateDatabase(DateBaseName, MODE_PRIVATE, null);
		sql_DB.execSQL("CREATE TABLE IF NOT EXISTS emp(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT NOT NULL, DEPT TEXT NOT NULL,SALARY TEXT NOT NULL,JOINDATE DATETIME NOT NULL)");
		
		btnSave.setOnClickListener(this);
		btnView.setOnClickListener(this);
			
			
	}
	public void onClick(View view)

	{
				
		if (view == btnSave)
		{
			addEmployee();
			clearText();
			
		}
		if(view == btnView)
		{
			Intent intent = new Intent(this, EmployeeActivity.class);
			startActivity(intent);
		}
	}

	
	public void showMessage(String title, String message)
	{
		Builder builder = new Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
		
	}

	
	@SuppressLint("NewApi")
	public boolean inputs(String name,String id)
	{
		if (name.isEmpty())
		{
			editTxtName.setError("Please Enter the name");
			editTxtName.requestFocus();
			return false;
			
		}
		
		if (id.isEmpty() || Integer.parseInt(id)<=0)
		{
			editTxtSalary.setError("Please Enter empId");
			editTxtSalary.requestFocus();
			return false;
			
		}
		return true;
	}
	
	private void addEmployee()
 
	{
		String name = editTxtName.getText().toString().trim();
		String id = editTxtSalary.getText().toString().trim();
		String dept = spinnerEmp.getSelectedItem().toString();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String JoiningDate = sdf.format(cal.getTime());
		
		
		if (inputs(name, id)) 
		{

			String insertQuery = "INSERT INTO emp\n "+
			"(NAME,DEPT,SALARY,JOINDATE)\n"+"VALUES\n"+"(?,?,?,?);";
			sql_DB.execSQL(insertQuery, new String[]{name,dept,id,JoiningDate});
			Toast.makeText(this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
			
		}
	}
	
	public void clearText()
	{
		editTxtSalary.setText("");
		editTxtName.setText("");
		
	}
	
	
//	public void onClick(View view)
//	{
//		switch (view.getId())
//		{
//		case R.id.btnAdd:
//			addEmployee();
//			break;
//		case R.id.textViewEmployees:
//			startActivity(new Intent(this, EmployeeActivity.class));
//			break;
//		
//		default:
//			break;
//		}
//		
//	}
	
//	private void createEmployeeTable() 
//	{
//		sql_DB.execSQL("CREATE TABLE IF NOT EXISTS employees(\n" + 
//				
//				" name varchar(200) NOT NULL,\n" +
//				" department varchar(200) NOT NULL,\n" +
//				" salary double NOT NULL\n" +");");
//		
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
