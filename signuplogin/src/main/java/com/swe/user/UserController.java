package com.swe.user;

import java.sql.DriverManager;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
@Controller
public class UserController {
	
	
	
	public static Student CurrentStudent = new Student();
	public static Teacher CurrentTeacher = new Teacher();

	@RequestMapping("/SignUp")
	public String MainSignUp() {

		return "mainSignup";
	}
	
	@RequestMapping("/Login")
	public String MainLogin() {

		return "mainlogin";
	}
	
	@RequestMapping("/loginAsStudent") // 34an law bn test e7na be 2edena el usecase dy
	public String LoginAsStudent() {
		return "StudentLogin";
	}

	@RequestMapping("/SignUpAsStudent") // 34an law bn test e7na be 2edena el usecase dy
	public String Student() {

		return "StudentSignUp";
	}

	@RequestMapping("/StudentSignUp.html") // 34an law gayen mn el main signup by7ot el page fel url fo2 fa 34an y3ml 3leha map we yrg3 dy
	public String Studentsignup() {
		return "StudentSignUp";
	}
	
	@RequestMapping("/StudentLogin.html") // 34an law gayen mn el main signup by7ot el page fel url fo2 fa 34an y3ml 3leha map we yrg3 dy
	public String Studentlogin() {
		return "StudentLogin";
	}
	
	@RequestMapping("/signupstudent")
	public ModelAndView SignupAsStudent(@RequestParam("Fnamesignup") String username,
			@RequestParam("passwordsignup") String password, @RequestParam("emailsignup") String
			email, @RequestParam("age") int age, @RequestParam("gendersignup") String gender, @RequestParam("level") String level) throws SQLException 
	{

        ModelAndView mv = new ModelAndView();
		mv.setViewName("StudentSignUp");
		Student user = new Student();
		user.setAge(age);
		user.setName(username);
		user.setGender(gender);
		user.setPassword(password);
		user.setEducationLevel(level);
		user.setEmail(email);
		
		boolean flag = true;
		flag = user.CreatAccount();
		
		if (flag == false) {
			mv.addObject("result", "This UserName Is Already In Use ");
		} 
		
		else 
		{
			mv.setViewName("afterLoginStudent"); // go to home page 
			CurrentStudent = user ;
		}

		return mv;
	}


	@RequestMapping("/studentlogin")
	public ModelAndView LoginAsStudent(@RequestParam("username") String username,
			@RequestParam("password") String password) throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("StudentLogin");
		Student user = new Student();
		user.setName(username);
		user.setPassword(password);
		boolean found = user.loginAsStudent();
		
		if (found== false) {
			mv.addObject("result", "User Name OR password is invalid , Please Try again ");
		} 
		
		else 
		{
			mv.setViewName("afterLoginStudent");
			CurrentStudent = user ;
		} 
		
	return mv;
	
	}
	

	@RequestMapping("/SignUpAsTeacher")
	public String Teacher() {

		return "TeacherSignUp";
	}
	
	
	@RequestMapping("/TeacherSignUp.html")
	public String Teachersigun() {

		return "TeacherSignUp";
	}
	
	@RequestMapping("/loginAsTeacher")
	public String loginAsTeacher() {
		return "TeacherLogin";
	}
	

	@RequestMapping("/TeacherLogin.html")
	public String loginAsTeacher2() {
		return "TeacherLogin";
	}
	
	@RequestMapping("/signupteacher")
	public ModelAndView SignUpAsTeacher(@RequestParam("Fnamesignup") String username,
			@RequestParam("passwordsignup") String password, @RequestParam("emailsignup") String email,
			@RequestParam("age") int age, @RequestParam("gendersignup") String gender,
			@RequestParam("address") String specialization, Model model) throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("TeacherSignUp");
		Teacher user = new Teacher();
		user.setAge(age);
		user.setName(username);
		user.setGender(gender);
		user.setPassword(password);
		user.setEducationEmail(email);
		user.setSpecialization(specialization);
		
		boolean flag = true;
		flag = user.CreatAccount();
		if (flag == false) {
			mv.addObject("result", "This UserName is Already in use , Please try again ! ");
		} 
		
		else {
			
			mv.setViewName("afterLoginTeacher"); // home
			CurrentTeacher = user ;
		}
		
		return mv;
	}
	
	@RequestMapping("/loginteacher")
	public ModelAndView LoginAsTeacher(@RequestParam("username") String username,
			@RequestParam("password") String password) throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("TeacherLogin");
		Teacher user = new Teacher();
		user.setName(username);
		user.setPassword(password);
		boolean found = user.loginAsTeacher();
		
		if (found== false) {
			mv.addObject("result", "User Name OR password is invalid , Please Try again ");
		} 
		
		else 
		{
			mv.setViewName("afterLoginTeacher");
			CurrentTeacher = user ; 
		} 
		
	return mv;
	
	}
	

}
