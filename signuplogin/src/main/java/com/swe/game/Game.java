package com.swe.game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.swe.course.Course;
import com.swe.dbConnections.DBConnection;
import com.swe.user.Teacher;
import com.swe.user.UserController;

public class Game {
    
	
	String CourseName , GameName , Provider ;
	int NumOfQuestions ;
	ArrayList<QuestionAndAns> QuestionsAndAnswers = new ArrayList<QuestionAndAns>();
	//ArrayList<String> Comments = new ArrayList<String>(); // el attribute eli hay7sal feeh l changes
	//Teacher Observer = new Teacher() ; // el attribute eli hay7sal feeh l changes

//	
//	public void addComment (String comment)
//	{
//		this.Comments.add(comment);
//		// insert the comment in DB then notify the teacher
//		NotifyTeacher();
//	}
//	
//	public void NotifyTeacher()
//	{
//		Observer
//	}
//	
//	
//	public ArrayList<String> getComments() {
//		return Comments;
//	}
//	public void setComments(ArrayList<String> comments) {
//		Comments = comments;
//	}
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public String getGameName() {
		return GameName;
	}
	public void setGameName(String gameName) {
		GameName = gameName;
	}
	public String getProvider() {
		return Provider;
	}
	public void setProvider(String provider) {
		Provider = provider;
	}
	public int getNumOfQuestions() {
		return NumOfQuestions;
	}
	public void setNumOfQuestions(int numOfQuestions) {
		NumOfQuestions = numOfQuestions;
	}
	public ArrayList<QuestionAndAns> getQuestionsAndAnswers() {
		return QuestionsAndAnswers;
	}
	
	public void setQuestionsAndAnswers(ArrayList<QuestionAndAns> questionsAndAnswers) {
		QuestionsAndAnswers = questionsAndAnswers;
	}
	
	public boolean insertGame () throws SQLException
	{  
		
		boolean inserted = false ;
		if (this.isUnique())
		{

			String query = "insert into game (coursename,game_name,numofquestions,provider) values('"+ this.getCourseName()+"','"+this.getGameName()+"',"+this.getNumOfQuestions()+",'"+this.getProvider()+"');" ;
			DBConnection.insert(query);
			for (int i=0 ; i<QuestionsAndAnswers.size(); i++)
			{
				QuestionsAndAnswers.get(i).insertQuestion();
				
			}
			
			inserted = true ;
			
		}
		
			
		return inserted ;
		
	}
	
	public boolean isUnique() throws SQLException
	{
		boolean unique = true ;
		
		 String query = ("Select game_name from game where game_name  = '" + this.getGameName() + "' ; ");
		 PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);
		 
		 ResultSet rs = stmt.executeQuery(query);
		if (rs.next())
		{
			unique = false;
		}
		
		return unique ;
			
	}
	
	
	public ArrayList<Game> GetCourseGames(String coursename) throws SQLException
	{	
		
		 ArrayList<Game> List = new ArrayList<Game>();
		 String query = ("Select * from game where coursename = '"+coursename+"' ; ");
		 Statement stmt = null ;
			
		    try
		    {
		    	stmt = DBConnection.getConnection().createStatement();
		    	ResultSet res = stmt.executeQuery(query);
		    	while (res.next())
		    	{   
		    		Game cur = new Game() ;
		    	    cur.setGameName(res.getString("game_name"));
		    		cur.setCourseName(res.getString("coursename"));
		    		cur.setNumOfQuestions(Integer.parseInt(res.getString("numofquestions")));
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
	
	public void CopyGame()
	{
		String NewGame =getGameName()+"copy";
		String query = "insert into game (coursename,game_name,numofquestions,provider) values('"+ this.getCourseName()+"','"+NewGame+"',"+this.getNumOfQuestions()+",'"+this.getProvider()+"');" ;		
	}
	
	
}
