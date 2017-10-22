package com.example.databaseexample;

public class Employee 
{

	int id;
	String name, dept, joiningDate,salary;
	
	
	public Employee(int id, String name,String dept,String salary, String joiningDate){
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.joiningDate = joiningDate;
		this.salary = salary;
	}
	
	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}
	 
	public String getDept(){
		return dept;
	}
	
	public String getSalary(){
		return salary;
	}
	
	public String getJoiningDate(){
		return joiningDate;
	}
	
	
	
	
}
