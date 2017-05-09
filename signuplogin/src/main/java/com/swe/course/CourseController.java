package com.swe.course;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.swe.user.UserController;

@Controller
public class CourseController {

	@RequestMapping("/addCourseForm.html") // lama yekon gay aslan mn l page bta3t el teacher
	public String Page() 
	{
		return "addCourseForm" ;		
	}
	
	@RequestMapping("/addcrs") // testing lena
	public String Page1() 
	{
		return "addCourseForm" ;		
	}
	
	@RequestMapping("/resultaddcourse")	
	public ModelAndView AddCourse(@RequestParam("coursename") String coursename, @RequestParam("field") String field) throws SQLException
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addCourseForm");
		Course c = new Course();
		c.setField(field);
		c.setCourseName(coursename);	
		c.setProvider(UserController.CurrentTeacher.getName()); // le teacher eli howa 3amel login aw sign up dlwa2ty 
		if (c.insertCourse())
		{
		   mv.addObject("result", " Your Course is created Successfully ^_^ ! ");
		   return new ModelAndView("redirect:" + "http://localhost:8080/ShowTeacherCourses");
		}
		
		else 
		{
		    mv.addObject("result", " This Course name is already in use :( ! ");
		    mv.setViewName("addCourseForm");
		}
		
		return mv;
	}
	
	@RequestMapping("/TeacherCourses.html") // testing lena
	public ModelAndView showteachercourses() 
	{   
		return new ModelAndView("redirect:" + "http://localhost:8080/ShowTeacherCourses");
	}
	
	 @RequestMapping(value = "/ShowTeacherCourses", method = RequestMethod.GET) // show courses of the teacher
	public ModelAndView ShowTeacherCourses() throws SQLException
	{   
		ModelAndView mv = new ModelAndView();
		mv.setViewName("TeacherCourses");		
		ArrayList<Course> CoursesList = new ArrayList<Course>();
		Course course = new Course() ;
		CoursesList = course.getCurTeacherCourses();
		mv.addObject("courses" , CoursesList);
		return mv ;
		
	}
	
	 @RequestMapping("/AllCourses.html") // testing lena
	public ModelAndView showallcourses() 
		{   
			return new ModelAndView("redirect:" + "http://localhost:8080/ShowAllCourses");
		}
	 
	 @RequestMapping(value = "/ShowAllCourses", method = RequestMethod.GET) // show all courses in the system
	public ModelAndView ShowAllCourses() throws SQLException
		{   
			ModelAndView mv = new ModelAndView();
			mv.setViewName("AllCourses");		
			ArrayList<Course> CoursesList = new ArrayList<Course>();
			Course course = new Course() ;
			CoursesList = course.getAllCourses();
			mv.addObject("courses" , CoursesList);
			return mv ;
			
		} 
	
}
