package com.example.demo.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

//import org.hibernate.mapping.Set;


@Entity
public class Students {

	@Id
	private Integer student_id;
	
	
	private String user;
	
	
	private String name;
	
	
	private String address;
	
	
	private String year;
	
	
	private String major;
	
	private String dorm_loc;
	
	public Students(){
		
	}
	public Students(int student_id, String user, String name, String address, String year,String major,String dorm_loc) {
		this.student_id=student_id;
		this.user=user;
		this.name=name;
		this.address=address;
		this.year=year;
		this.major=major;
		this.dorm_loc = dorm_loc;
	}


	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
		name = "enrolled",
		joinColumns = @JoinColumn(name = "student_id"),
		inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> enrolledcourses;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
		name = "chat_Rooms",
		joinColumns = @JoinColumn(name = "student_id"),
		inverseJoinColumns = @JoinColumn(name = "chat_id"))
	private List<chat> chatRooms;
	
	public List<chat> getchatRooms(){
		return chatRooms;
	}
	
	public List<Course> getEnrolledCourses(){
		return enrolledcourses;
	}
	
	public void setEnrolledCourses(List<Course> enrolledcourses) {
		this.enrolledcourses = enrolledcourses;
	}
	
	//getters
	public Integer getStudent_id() {
		return student_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getYear() {
		return year;
	}
	
	public String getMajor() {
		return major;
	}
	
	public String getdorm_loc() {
		return dorm_loc;
	}
	
	//setters
	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setYear(String year){
		this.year = year;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	public void setDorm(String dorm_loc) {
		this.dorm_loc = dorm_loc;
	}
	
	
	
	
			
	
	
}
