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
import com.swe.user.UserController;

public class Game {

	String CourseName, GameName, Provider, comment;
	int NumOfQuestions;
	ArrayList<QuestionAndAns> QuestionsAndAnswers = new ArrayList<QuestionAndAns>();
	ArrayList<String> Comments = new ArrayList<String>();

	public String getComment() {
		return comment;
	}

	public void setComment(String Comment) {
		comment = Comment;
	}

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

	public void CopyGame(ArrayList<QuestionAndAns> questionsAndAnswers) throws SQLException {
		System.out.println("*******************************************************************");
		String NewGame = getGameName() + "copy";
		System.out.println("NewGame  = " + NewGame);
		System.out.println("this.getNumOfQuestions() = " + this.getNumOfQuestions());
		System.out.println("this.getCourseName() = " + this.getCourseName());
		System.out.println("this.getProvider() " + this.getProvider());
		String query = "insert into game (coursename,game_name,numofquestions,provider) values('" + this.getCourseName()
				+ "','" + NewGame + "'," + this.getNumOfQuestions() + ",'" + this.getProvider() + "');";
		DBConnection.insert(query);
		System.out.println("Modellllllllllllllllllllllllllll = " + questionsAndAnswers.size());

		for (int i = 0; i < questionsAndAnswers.size(); i++) {
			questionsAndAnswers.get(i).insert_Copy_Question(NewGame);

		}

	}

	public void Delete_Game() throws SQLException {

		String query = "delete from paly where Game_name = '" + this.GameName + "';";
		DBConnection.insert(query);
		String query1 = "delete from QuestionAndAnswers where game_name = '" + this.GameName + "';";
		DBConnection.insert(query1);
		String query2 = "delete from game where game_name = '" + this.GameName + "';";
		DBConnection.insert(query2);
	}

	public void Add_comment(String gamename) throws SQLException {
		System.out.println("this.getGameName()  = " + gamename);
		System.out.println("this.getComment()    = " + this.getComment());
		String query = "insert into comment(game_name,game_comment)values('" + gamename + "','" + this.getComment()
				+ "');";
		DBConnection.insert(query);

	}

	public boolean insertGame() throws SQLException {

		boolean inserted = false;
		if (this.isUnique()) {

			String query = "insert into game (coursename,game_name,numofquestions,provider) values('"
					+ this.getCourseName() + "','" + this.getGameName() + "'," + this.getNumOfQuestions() + ",'"
					+ this.getProvider() + "');";
			DBConnection.insert(query);
			for (int i = 0; i < QuestionsAndAnswers.size(); i++) {
				QuestionsAndAnswers.get(i).insertQuestion();

			}

			inserted = true;

		}

		return inserted;

	}

	public boolean isUnique() throws SQLException {
		boolean unique = true;

		String query = ("Select game_name from game where game_name  = '" + this.getGameName() + "' ; ");
		PreparedStatement stmt = (PreparedStatement) DBConnection.getConnection().prepareStatement(query);

		ResultSet rs = stmt.executeQuery(query);
		if (rs.next()) {
			unique = false;
		}

		return unique;

	}

	public ArrayList<Game> GetCourseGames(String coursename) throws SQLException {

		ArrayList<Game> List = new ArrayList<Game>();
		String query = ("Select * from game where coursename = '" + coursename + "' ; ");
		Statement stmt = null;

		try {
			stmt = DBConnection.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while (res.next()) {
				Game cur = new Game();
				cur.setGameName(res.getString("game_name"));
				cur.setCourseName(res.getString("coursename"));
				cur.setNumOfQuestions(Integer.parseInt(res.getString("numofquestions")));
				cur.setProvider(res.getString("provider"));
				List.add(cur);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return List;

	}

	public ArrayList<String> GetGameComments(String gamename) throws SQLException {
		ArrayList<String> List = new ArrayList<String>();
		String query = ("Select * from comment where game_name = '" + gamename + "' ; ");
		Statement stmt = null;

		try {
			stmt = DBConnection.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while (res.next()) {
				String Comment = res.getString("game_comment");
				List.add(Comment);
				System.out.println("------------------------------  "  + Comment);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return List;

	}

}
