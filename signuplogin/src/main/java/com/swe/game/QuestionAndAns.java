package com.swe.game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.swe.dbConnections.DBConnection;

public class QuestionAndAns {
	
	String GameName ,Question , Ans1 , Ans2 , Ans3 , CorrectAns ;

	public String getGameName() {
		return GameName;
	}
    
	public void setGameName(String gameName) {
		GameName = gameName;
	}
  
	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public String getAns1() {
		return Ans1;
	}

	public void setAns1(String ans1) {
		Ans1 = ans1;
	}

	public String getAns2() {
		return Ans2;
	}

	public void setAns2(String ans2) {
		Ans2 = ans2;
	}

	public String getAns3() {
		return Ans3;
	}

	public void setAns3(String ans3) {
		Ans3 = ans3;
	}

	public String getCorrectAns() {
		return CorrectAns;
	}

	public void setCorrectAns(String correctAns) {
		CorrectAns = correctAns;
	}
	
	public void insertQuestion () throws SQLException
	{
		String query = "insert into QuestionAndAnswers (game_name , question , answer1 , answer2 , answer3 , rightanswer) values('"+this.GameName+"','"+this.Question+"','"+this.Ans1+"','"+this.Ans2+"','"+this.Ans3+"','"+this.CorrectAns+"'); " ;
		DBConnection.insert(query);	
		
	}
	
	public void insert_Copy_Question (String GameName) throws SQLException
	{
		String query = "insert into QuestionAndAnswers (game_name , question , answer1 , answer2 , answer3 , rightanswer) values('"+GameName+"','"+this.Question+"','"+this.Ans1+"','"+this.Ans2+"','"+this.Ans3+"','"+this.CorrectAns+"'); " ;
		DBConnection.insert(query);	
		
	}
	
	public static ArrayList<QuestionAndAns> getGameQuestions(String gamename) throws SQLException
	{	
		
		 ArrayList<QuestionAndAns> List = new ArrayList<QuestionAndAns>();
		 String query = ("Select * from questionandanswers where game_name = '"+gamename+"' ; ");
		 Statement stmt = null ;
			
		    try
		    {
		    	stmt = DBConnection.getConnection().createStatement();
		    	ResultSet res = stmt.executeQuery(query);
		    	while (res.next())
		    	{   
		    		QuestionAndAns cur = new QuestionAndAns() ;
		    		cur.setGameName(gamename);
		    		cur.setQuestion(res.getString("question"));
		    		cur.setAns1(res.getString("answer1"));
		    		cur.setAns2(res.getString("answer2"));
		    		cur.setAns3(res.getString("answer3"));
		    		cur.setCorrectAns(res.getString("rightanswer"));
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
	
	

}
