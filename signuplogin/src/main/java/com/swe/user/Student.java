package com.swe.user;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.swe.dbConnections.DBConnection;

public class Student extends User {

	String EducationLevel;

	public Student()
	{
		
	}
	
	public Student(String EducationLevel) {
		this.EducationLevel = EducationLevel;
	}

	public String getEducationLevel() {
		return EducationLevel;
	}

	public void setEducationLevel(String EducationLevel) {
		this.EducationLevel = EducationLevel;
	}

	public boolean CreatAccount() throws SQLException {
		boolean flag = true;
	    String query = ("Select student_name  from student where student_name = '" + this.getName() + "'; ");
		PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next()) {
			flag = false;
		}
		
		else{
		String insert_query = "insert into student values('" + this.getName() + "','" + this.getPassword() + "','"
				+ this.getEmail() + "'," + this.getAge() +" ,'" + this.getGender() + "','" + this.getEducationLevel()
				+ "');";
		
		DBConnection.insert(insert_query);
		}
		return flag;
	}

	public boolean loginAsStudent() throws SQLException {
		
		boolean found = false;
		String query = ("Select student_name from student where student_name = '" + this.getName()
				+ "' and password = '" + String.valueOf(this.password) + "' ; ");
		PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		
		if (rs.next())
		{
			found=true ;
			
		}
		
		return found;
	}
	
	
	boolean VerifyEmail() {
		
		return this.email.contains("@");
	}

}
