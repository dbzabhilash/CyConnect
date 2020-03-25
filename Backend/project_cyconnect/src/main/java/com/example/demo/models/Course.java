package com.example.demo.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

//import org.hibernate.mapping.Set;
//some test change 

/**
 * 
 * @author abirm
 *This entity is a model of a list of courses you can enroll at the university
 */
@Entity
public class  Course{

	@Id
	@Column(name = "course_id")
	Integer id;
	
	public Integer getCourse_id() {
		return id;
	}
	
	@Column(name = "course_name")
	private String name;
	
	public String getCourse_name() {
		return name;
	}
	
	@Column(name = "section")
	private String section;
	
	public String getSection() {
		return section;
	}
	
	@Column(name = "professor")
	private String professor;
	
	public String getProfessor() {
		return professor;
	}
	
	@Column(name = "credits")
	private Integer credits;
	
	public Integer getCredits() {
		return credits;
	}
	
	@Column(name = "time")
	private String time;
	
	public String getTime() {
		return time;
	}
	
	
	@Column(name = "connected")
	private int connected;
	
	@Column(name = "day") //example MWR (monday,wed,thursday), so frontend can read this and place this class in the calendar
	private String day;
	
	public String getDay() {
		return day;
	}
	
	
	@ManyToMany(mappedBy = "enrolledcourses")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	List<Students> studentList;
	
	public void setStudentList(List<Students> studentList) {
		this.studentList = studentList;
	}
	
	public List<Students> getStudentList(){
		return studentList;
	}
	
	

	public int getConnected() {
		return connected;
	}
	public void incrementConnected() {
	
		connected++;
	}
	public void decrementConnected() {
		connected--;
	}
	public String toString() {
		return id.toString();
	}
	
	
	public Course() {
		
	}
	

}