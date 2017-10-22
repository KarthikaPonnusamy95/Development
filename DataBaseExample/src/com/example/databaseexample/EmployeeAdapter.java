package com.example.databaseexample;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeAdapter extends ArrayAdapter<Employee> 
{

	Context context;
	int listLayout;
	List<Employee> empList;
	SQLiteDatabase sql_database;
	Button btnDelete,btnEdit;
	EmployeeAdapter empAdapter;
	
	public EmployeeAdapter(Context context, int listLayout, List<Employee> empList, SQLiteDatabase sq_database)
	{
		
		super(context, listLayout,empList);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listLayout =listLayout;
		this.empList = empList;
		this.sql_database = sq_database;
	}
	
	
	@NonNull
	@Override
	public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(listLayout, null);
		
		final Employee employee = empList.get(position);
		
		TextView txtViewName = (TextView) view.findViewById(R.id.txtName);
		TextView txtViewDept = (TextView) view.findViewById(R.id.txtSector);
		TextView txtViewSalary = (TextView) view.findViewById(R.id.txtSalary);
		TextView txtViewjoinDate = (TextView) view.findViewById(R.id.txtjoinDate);
		
		txtViewName.setText(employee.getName());
		txtViewSalary.setText(employee.getSalary());
		txtViewDept.setText(employee.getDept());
		txtViewjoinDate.setText(employee.getJoiningDate());
		
		btnDelete = (Button) view.findViewById(R.id.btnDelete);
		btnEdit = (Button) view.findViewById(R.id.btnEditEmp);
		
//		btnDelete.setOnClickListener(this);
//		btnEdit.setOnClickListener(this);

		btnEdit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				updateEmployee(employee);
			}
		});
		
		btnDelete.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are You Sure?");
				builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String sql = "DELETE FROM emp WHERE id = ?";
						sql_database.execSQL( sql,new Integer[]{employee.getId()});
						reloadEmployeesFromDatabase();
						
					}
				});
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
			
			
		});
		
		return view;
	}

	
	
	@SuppressLint("NewApi")
	private void updateEmployee(final Employee employee)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.update_employee, null);
		builder.setView(view);
		
		final EditText editTxtName = (EditText) view.findViewById(R.id.editTextName);
		final EditText editTxtsalary = (EditText) view.findViewById(R.id.editTextSalary);
		final Spinner spinnerDept = (Spinner) view.findViewById(R.id.spinnerDepartment);
		
		editTxtName.setText(employee.getName());
		editTxtsalary.setText(employee.getSalary());
		
		
		Button btnUpDate = (Button) view.findViewById(R.id.buttonUpdateEmp);
		
		final AlertDialog dialog = builder.create();
		dialog.show();
		
		btnUpDate.setOnClickListener(new View.OnClickListener()
		{
			
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				String name = editTxtName.getText().toString().trim();
				String salary = editTxtsalary.getText().toString().trim();
				String dept = spinnerDept.getSelectedItem().toString();
				
				if (name.isEmpty())
				{
					editTxtName.setError("Name cant be blank");
					editTxtName.requestFocus();
					return;
					
				}
				
				if (salary.isEmpty())
				{
					editTxtsalary.setError("Salary cant be blank");
					editTxtsalary.requestFocus();
					return;
					
				}
				
				 
				
				String sql = "UPDATE emp \n" +
				"SET NAME = ?, \n" +
						"DEPT = ?, \n" + 
				"SALARY = ? \n" + 
						"WHERE ID = ?;\n";
				
				sql_database.execSQL(sql,new String[]{name,dept, salary, String.valueOf(employee.getId())});
				
				Toast.makeText(context, "Employee Updated", Toast.LENGTH_SHORT).show();
				reloadEmployeesFromDatabase();
				dialog.dismiss();
			}
		});
		
	}
	
	
	private void reloadEmployeesFromDatabase()
	{
		
		Cursor cursorEmp = sql_database.rawQuery("SELECT * FROM emp", null);
		
		if (cursorEmp.moveToFirst()) 
		{
			empList.clear();
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
		notifyDataSetChanged();
		//empAdapter.notifyDataSetChanged();
		
		
	}
	
}
