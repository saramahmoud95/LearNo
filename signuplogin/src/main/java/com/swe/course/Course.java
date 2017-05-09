package com.swe.course;
import com.mysql.jdbc.PreparedStatement;
import com.swe.dbConnections.*;
import com.swe.user.UserController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
   
	String CourseName , Provider , Field ;
    
	public Course (){}
	
	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public String getProvider() {
		return Provider;
	}

	public void setProvider(String provider) {
		Provider = provider;
	}

	public String getField() {
		return Field;
	}

	public void setField(String field) {
		Field = field;
	}
	

	
	public boolean insertCourse () throws SQLException
	{   
		boolean inserted = true ;
		
		if (isUnique())
		{
			String query = "insert into course( provider , coursename , Field ) values('" + this.getProvider() + "','" + this.getCourseName()
				+ "' , '"+this.getField()+"');";
			DBConnection.insert(query);		
		}
		
		else 
		{
			inserted = false ;
			
		}
		
		return inserted ;
	}
	
	
	public boolean isUnique() throws SQLException
	{
		boolean unique = true ;
		
		 String query = ("Select coursename from course where coursename = '" + this.getCourseName() + "' ; ");
		 PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);
		 ResultSet rs = stmt.executeQuery(query);
		if (rs.next())
		{
			unique = false;
		}
		
		return unique ;
			
	}
	
	
	public ArrayList <Course> getAllCourses () throws SQLException
	{
		ArrayList<Course> List = new ArrayList<Course>();
		String Query = " Select * from Course "; 
		Statement stmt = null ;
		
	    try
	    {
	    	stmt = DBConnection.getConnection().createStatement();
	    	ResultSet res = stmt.executeQuery(Query);
	    	while (res.next())
	    	{   
	    		Course cur = new Course() ;
	    	    cur.setCourseName(res.getString("coursename"));
	    		cur.setField(res.getString("Field"));
	    		cur.setProvider(res.getString("provider"));
	    		List.add(cur);	
	    	}

	    }
		catch (SQLException e)
	    {
          e.printStackTrace(); 
	    }
	    finally {
			if (stmt!=null)
			{
				stmt.close();
			}
		}
		
		return List ;
	}
	
	public ArrayList <Course> getCurTeacherCourses () throws SQLException
	{
		ArrayList<Course> List = new ArrayList<Course>();
		String Query = " Select * from Course where provider = '"+UserController.CurrentTeacher.getName()+"' ; "; 
		Statement stmt = null ;
		
	    try
	    {
	    	stmt = DBConnection.getConnection().createStatement();
	    	ResultSet res = stmt.executeQuery(Query);
	    	while (res.next())
	    	{   
	    		Course cur = new Course() ;
	    	    cur.setCourseName(res.getString("coursename"));
	    		cur.setField(res.getString("Field"));
	    		cur.setProvider(res.getString("provider"));
	    		List.add(cur);	
	    	}

	    }
		catch (SQLException e)
	    {
          e.printStackTrace(); 
	    }
	    finally {
			if (stmt!=null)
			{
				stmt.close();
			}
		}
		
		return List ;
		System.out.println("Saraaa GitHub");
	}
	
	
	
	
}

