package com.swe.user;

import java.sql.SQLException;

import com.swe.game.Subject;

public class User extends Observer {
	
	String name ;
	String password ;
	String email ;
	int age ;
	String gender ;
	
	public User (){}
	
	public User(Subject subject)
	{
		this.subject = subject ;
		this.subject.attach(this);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	

}
