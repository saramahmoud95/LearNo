package com.swe.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;

import com.mysql.jdbc.PreparedStatement;
import com.swe.dbConnections.DBConnection;
import com.swe.game.Subject;
import com.mysql.jdbc.Connection;

public class Teacher extends User {

	String Specialization;

	public Teacher(){
		
		super();
	}
	
	public Teacher(Subject subject){
		
		super(subject);
	}

	
	
	@Override
	public void update() {
            
		 System.out.println("Comment : " + this.subject.getComment());
	}
	
	
	public void setEducationEmail(String Specialization) {
		this.Specialization = Specialization;
	}

	public String getSpecialization() {
		return Specialization;
	}

	public void setSpecialization(String Specialization) {
		this.Specialization = Specialization;
	}

	boolean VerifyTeacher() {
		return this.email.contains(".edu.eg") && this.email.contains("@");
	}

	public boolean CreatAccount() throws SQLException {
		boolean flag = true;

		String query = ("Select teacher_name  from teacher where teacher_name = '" + this.getName() + "'; ");
		PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		
		if (rs.next()) {
			flag = false;
		}

		else {
			String insert_query = "insert into teacher values('" + this.getName() + "','" + this.getPassword() + "','"
					+ this.getEmail() + "'," + this.getAge() + ",'" + this.getGender() + "','"
					+ this.Specialization + "');";
			
			DBConnection.insert(insert_query);
		}

		return flag;

	}

	public boolean loginAsTeacher() throws SQLException {
		boolean flag = false;
		String query = ("Select teacher_name  from teacher where teacher_name = '" + this.getName()
				+ "' and password = '" + String.valueOf(this.password) + "' ; ");
		PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		
		if (rs.next())
		{
			flag=true ;
			
		}
		return flag;

	}

}
