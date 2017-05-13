package com.swe.game;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.catalina.startup.UserConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.swe.course.Course;
import com.swe.dbConnections.DBConnection;
import com.swe.user.UserController;

@Controller
public class GameController {


	public static String CurProvider = "";

	public static int numofquestions = 0 , score =0  ; // num of questions 34an ytl3hom fe play game , score bta3 el cur user 
	
	public static  ArrayList<QuestionAndAns> Questions ; // Array Of Questions bta3 el game eli byl3bha 
	
	public  static String CurGameName = ""; // esm el game eli e5tarha 34an yl3b 
	
	public static int NumberOfQuestions  ; //  num of questions 34an a3rf howa da5al kam q aslan fe play game
	
	Game Curgame ; // cur game eli byd5lha 
	
	@RequestMapping("/addgame") // test be 2edena
	public String index()
	{
		return "addGameForm";
	}
	
	@RequestMapping("/addGameForm.html") // test be 2edena
	public String addgame() 
	{
		return "addGameForm";
	}
	
	
	
	Game game = new Game();

	/****************************************************************************************/
	@RequestMapping(value = "/Copy/{name}", method = RequestMethod.GET)
	public ModelAndView Copy(@PathVariable String name) throws SQLException {
		ArrayList<Game> games = new ArrayList<Game>();
		//Game game = new Game();
		// name--->course name
		game.setCourseName(name);
		game.setGameName(CurGameName);
		game.setNumOfQuestions(NumberOfQuestions);
		System.out.println("game.QuestionsAndAnswers in copy  " + Questions.size());

		CurProvider=getProvider(name);
		game.setProvider(CurProvider);
		game.CopyGame(Questions);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showGames");
		mv.addObject("games", games);
		return mv;
	}
		/****************************************************************************************/

	
		@RequestMapping(value = "/deletegame/{gamename}", method = RequestMethod.GET)
	public ModelAndView DeleteGame(@PathVariable String gamename) throws SQLException {

		ModelAndView mv = new ModelAndView();
		CurGameName = gamename;
		//Game game = new Game();

		game.setGameName(CurGameName);

		game.Delete_Game();
		mv.setViewName("showGames");
		return mv;
	}
			/****************************************************************************************/

@RequestMapping(value = "/Copy_Questions/{gamename}/{Num_Of_Q}/{Provider_name}", method = RequestMethod.GET)
	public ModelAndView Copy_Questions(@PathVariable String gamename, @PathVariable int Num_Of_Q,
			@PathVariable String Provider_name) throws SQLException {
		ModelAndView mv = new ModelAndView();
		CurGameName = gamename;
		Game game = new Game();
		game.setGameName(CurGameName);
		game.setProvider(Provider_name);
		game.setNumOfQuestions(Num_Of_Q);
		NumberOfQuestions=Num_Of_Q;
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("CurGameName + " + CurGameName);
		System.out.println("Provider_name  + " + Provider_name);
		System.out.println("Num_Of_Q   = " + Num_Of_Q);
		this.Questions = QuestionAndAns.getGameQuestions(CurGameName);
		game.QuestionsAndAnswers = Questions;
		System.out.println("game.QuestionsAndAnswers " + Questions.size());
		System.out.println("-------------------------------------------------------------------------");
		mv.setViewName("Teacher_Courses_Paste");
		ArrayList<Course> CoursesList = new ArrayList<Course>();
		Course course = new Course();
		CoursesList = course.getCurTeacherCourses();
		mv.addObject("courses", CoursesList);
		return mv;
	}
	
/****************************************************************************************/



@RequestMapping("/addcomment")
public ModelAndView AddComment(@RequestParam("comment") String comment)
	throws SQLException {
	
	ModelAndView mv = new ModelAndView();
	mv.setViewName("playgame1");
	System.out.println("Current game name = "  +CurGameName);
	game.setComment(comment);
	game.Add_comment(CurGameName);
	return mv;	
}


/****************************************************************************************/

	
	
	
	@RequestMapping(value="/showgames/{name}",method=RequestMethod.GET)
	public ModelAndView ShowGames(@PathVariable String name) throws SQLException
	{
		   ArrayList<Game> games = new ArrayList<Game>();
		   Game game = new Game() ;
		   games = game.GetCourseGames(name);  // get kol el games eli tb3 el course name dah 
		   ModelAndView mv=new ModelAndView();
		   mv.setViewName("showGames");
		   mv.addObject("games", games);
		   
		   
		   return mv;
	}
	
	
	
	@RequestMapping(value="/showgamewithremove/{name}",method=RequestMethod.GET)
	public ModelAndView ShowGamesToDelete(@PathVariable String name) throws SQLException
	{
		   ArrayList<Game> games = new ArrayList<Game>();
		   Game game = new Game() ;
		   games = game.GetCourseGames(name);  // get kol el games eli tb3 el course name dah 
		   ModelAndView mv=new ModelAndView();
		   mv.setViewName("showgamewithremove");
		   mv.addObject("games", games);
		   
		   return mv;
	}
	
	
	
	
//	 @RequestMapping("/EnterGame.html")
//	 public ModelAndView showenter()
//	 {   
//		 return new ModelAndView("redirect:" + "http://localhost:8080/EnterPlayGame");
//	 }
	
	
	 @RequestMapping("/EnterGame.html")
	 public String map()
	 {   
		 return "EnterGame" ;
	 }
	 
    @RequestMapping("/EnterPlayGame")
    public ModelAndView enterplaygame(@RequestParam("gname") String gamename) throws SQLException
	{      
		   ModelAndView mv=new ModelAndView();
		   mv.setViewName("playgame1"); // na2es feha el CSS 
    	  
		   System.out.println("game name = " + gamename);

		   Curgame.setGameName(gamename);
    	   
    	   if ( !Curgame.isUnique()) // el game dy mwgoda aslan y3ny md5l esmha sa7
    	   {   
    		   System.out.println("iffffffffffff 111111111");
        	   this.Questions = QuestionAndAns.getGameQuestions(gamename);
    		   CurGameName = gamename ;
    		   mv.setViewName("playgame1"); // na2es feha el CSS 
    		   // show the first question 
    		   mv.addObject("question", Questions.get(numofquestions).getQuestion());
    		   mv.addObject("choose1" , Questions.get(numofquestions).getAns1());
    		   mv.addObject("choose2" , Questions.get(numofquestions).getAns2());
    		   mv.addObject("choose3" , Questions.get(numofquestions).getAns3());
    		   mv.addObject("choose4" , Questions.get(numofquestions).getCorrectAns());
  
    	   }
    	   
    	   else 
    	   {   
    		   System.out.println("ay 7aga ya gd3an");
    		   mv.addObject("result", "This Game is not Found ! , Please Try again"); 
    		   mv.setViewName("EnterGame");

    	   }
    	   
		   return mv; 

	}

   /* @RequestMapping(value="/playgame/{gamename}",method=RequestMethod.GET)
	public ModelAndView PlayGame(@PathVariable String gamename) throws SQLException  
	{    
		   this.Questions = QuestionAndAns.getGameQuestions(gamename);
		   ModelAndView mv=new ModelAndView();
		   CurGameName = gamename ;
		   mv.setViewName("playgame1"); // na2es feha el CSS 
		   
		   // show the first question 
		   mv.addObject("question", Questions.get(numofquestions).getQuestion());
		   mv.addObject("choose1" , Questions.get(numofquestions).getAns1());
		   mv.addObject("choose2" , Questions.get(numofquestions).getAns2());
		   mv.addObject("choose3" , Questions.get(numofquestions).getAns3());
		   mv.addObject("choose4" , Questions.get(numofquestions).getCorrectAns());

		   return mv;
	}*/
    	@RequestMapping(value="/playgame/{gamename}",method=RequestMethod.GET)
	public ModelAndView PlayGame(@PathVariable String gamename) throws SQLException  
	{    
		   this.Questions = QuestionAndAns.getGameQuestions(gamename);
		   ModelAndView mv=new ModelAndView();
		   Game game = new Game() ;
		   CurGameName = gamename ;
		   ArrayList<String> gamesComment = new ArrayList<String>();
		   gamesComment = game.GetGameComments(gamename);
		   mv.setViewName("playgame1");
           mv.addObject("playgame1", gamesComment);
		   
		  // mv.setViewName("playgame1"); // na2es feha el CSS 
		   // show the first question 
		   mv.addObject("question", Questions.get(numofquestions).getQuestion());
		   mv.addObject("choose1" , Questions.get(numofquestions).getAns1());
		   mv.addObject("choose2" , Questions.get(numofquestions).getAns2());
		   mv.addObject("choose3" , Questions.get(numofquestions).getAns3());
		   mv.addObject("choose4" , Questions.get(numofquestions).getCorrectAns());

		   return mv;
	}
		
	@RequestMapping(value="/ShowQuestion",method=RequestMethod.GET)
	public ModelAndView ShowQuestion( @RequestParam("name") String choice ) throws SQLException
	  {
		   ModelAndView mv=new ModelAndView();
		   mv.setViewName("playgame1");
		   this.Questions = QuestionAndAns.getGameQuestions(CurGameName); // get the questions again
		 
		   if (choice.equals("choose4")) // l correct ans dymn rkm 4 
		   { 
			  score++; 
			  mv.addObject("msg" , "Correct Choice !");
		   }
		   else // wrong ans
		   {
			  mv.addObject("msg" , "Wrong Choice ! , Correct choice : " + Questions.get(numofquestions).getCorrectAns() );
		   }
		   
		   numofquestions++ ; // get the next question 
		   
		   if (this.numofquestions < this.Questions.size()) // law el counter eli bn3d beeh mawsle4 le num of questions fl cur game 
		   {
			   mv.addObject("question", this.Questions.get(numofquestions).getQuestion());
			   mv.addObject("choose1" , this.Questions.get(numofquestions).getAns1());
			   mv.addObject("choose2" , this.Questions.get(numofquestions).getAns2());
			   mv.addObject("choose3" , this.Questions.get(numofquestions).getAns3());
			   mv.addObject("choose4" , this.Questions.get(numofquestions).getCorrectAns());
			  
		   }
		   
		   else //  keda 5alas el game fa hay2olo el score bta3o kam 
		   {			 
			 return new ModelAndView("redirect:" + "http://localhost:8080/ShowScore");
		   }
		  
		   return mv;
	   }
	
	@RequestMapping(value="/ShowScore",method=RequestMethod.GET)
	public ModelAndView showscore() throws SQLException
	{    
		ModelAndView mv = new ModelAndView();
		savescore();
		mv.setViewName("showScore");
		mv.addObject("score" , score ) ;
        return mv ;
	}
	
	
	public void savescore () throws SQLException
	{

		String query = "insert into paly (Game_name, student_name , Score) values('"+CurGameName+"','"+UserController.CurrentStudent.getName()+"', "+score+") ;" ;
		DBConnection.insert(query);
	
	}
	
	public String getProvider(String coursename) throws SQLException // law el course m4 mawgod yb2a malo4 provider ... fa dy k2nha check 3ala en l course aslan mawgod
	{ 
		String Query = " Select provider from course where coursename = '"+coursename+"' ; "; 
		Statement stmt = null ;
		String provider = "";
		
	    try
	    {
	    	stmt = DBConnection.getConnection().createStatement();
	    	ResultSet res = stmt.executeQuery(Query);
	    	if (res.next())
	    	{   
	    		provider = res.getString("provider");
	    		
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
		
		return provider ;
		
	}

	@RequestMapping("/resultaddgame")
	public ModelAndView AddGame(@RequestParam("cname") String coursename , @RequestParam("gname") String gamename , @RequestParam("numofquestion") int numofquestions )
		throws SQLException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("addGameForm");
	    Curgame = new Game();
		String gameProvider = getProvider(coursename); // lazm yb2a el game provider howa nfso el course provider
		Curgame.setGameName(gamename); // to check eno game name is unique 
		
		if (gameProvider =="") // mfe4 provider lel course eli md5lo y3ny aslan el course dah m4 mawgod 
		{
		   mv.addObject("result", " This Course is not exists !");
		   mv.setViewName("addGameForm");

		}
		
		else if(!gameProvider.equals(UserController.CurrentTeacher.getName())) // law el course aslan mawgod .. bs el ragl eli 3aiz ydef feeh game m4 howa aslan eli me create el course dah ... teacher can add games in his own courses only
		{
			 mv.addObject("result", " Sorry , You Can not add game in this Course , because it is not yours :D !");
			 mv.setViewName("addGameForm");

		}
		else if (!Curgame.isUnique()) // law el game dy already mawgoda 34an maydrb4 fel Primary key bt3ha btkaren bel game name
		{
			mv.addObject("result", " This Game is already exists !");
			mv.setViewName("addGameForm");
		}
		
		else // haydef as2la 3ady 
		{
			Curgame.setCourseName(coursename);
			Curgame.setGameName(gamename);
			Curgame.setProvider(gameProvider);
			Curgame.setNumOfQuestions(numofquestions);
			NumberOfQuestions = numofquestions ;
			mv.setViewName("addQuestions");
			
		}
				
		return mv;
	}
	
	
	
	


	@RequestMapping("/addNextquestion")
	public ModelAndView AddQuestion(@RequestParam("quest") String question, @RequestParam("answer1") String Ans1,
			@RequestParam("answer2") String Ans2, @RequestParam("answer3") String Ans3,
			@RequestParam("correct") String correct) throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addQuestions");
	    
		for ( int i=1 ; i < NumberOfQuestions ; i++)
	    {
	    	QuestionAndAns curquestion = new QuestionAndAns();
	    	curquestion.setGameName(Curgame.getGameName());
	    	curquestion.setQuestion(question);
	    	curquestion.setAns1(Ans1);
	    	curquestion.setAns2(Ans2);
	    	curquestion.setAns3(Ans3);
	    	curquestion.setCorrectAns(correct);
	    	
	    	Curgame.QuestionsAndAnswers.add(curquestion);
	    	mv.addObject("result", " Question is added , Please Enter another one ! ");
		    NumberOfQuestions--;

	        return mv ;
	    	
	    }
		
		if (Curgame.insertGame())
		{   
	    	mv.addObject("result", "Game is created ^_^ !!");			
		}
		else 
		{
			mv.addObject("result", "Game Did not created !! ");

		}
		
		return mv;

	}

}
