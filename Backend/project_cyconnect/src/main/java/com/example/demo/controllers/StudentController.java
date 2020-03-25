//latest ready to merge(merge request)
package com.example.demo.controllers;

import com.example.demo.services.CourseEnroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors; 
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.example.demo.repositories.LoginRepository;
import com.example.demo.repositories.StudentsRepository;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.models.Students;
import com.example.demo.models.Course;
import com.example.demo.models.Login;


@RestController
public class StudentController {

	@Autowired
	private StudentsRepository studentRepo;
	@Autowired
	private LoginRepository loginRepo;
	@Autowired
	private CourseRepository courseRepo;
	//make logger later

	Students newstudent = new Students();

	//@PostConstruct
	@PostMapping(path = "/students/register")
	public Map register_student(@RequestBody Students student) {

		//String str = student.getUser();
		Integer student_id = loginRepo.findByUsername(student.getUser()).getId();

		newstudent.setStudent_id(student_id);
		newstudent.setName(student.getName());;
		newstudent.setAddress(student.getAddress());
		newstudent.setMajor(student.getMajor());
		newstudent.setYear(student.getYear());
		newstudent.setUser(student.getUser());
		newstudent.setDorm(student.getdorm_loc());
		studentRepo.save(newstudent);

		Map<String, String> success = Collections.singletonMap("Status","Successful");
		return success; //assume cannot fail registering, because login/register checks for duplicate username already!
	}



	//CourseEnroll newcourses = new CourseEnroll();
	@PostMapping(path = "/students/register_courses")
	public Students register_courses(@RequestBody CourseEnroll course) {
		
		Students currStud=studentRepo.findByUser(course.getUser());
		if (currStud == null) {
			return null;
		}
		Course newCrs=courseRepo.findCourse(course.getClassName(), course.getSect());
		if(!currStud.getEnrolledCourses().contains(newCrs)){
			currStud.getEnrolledCourses().add(newCrs);
			studentRepo.save(currStud);
		}
		
		return studentRepo.findByUser(course.getUser());
	}
//		Students currstudent = studentRepo.findByUser(courses.getUser()); //returns entity of student with this username
//		int sizeofcurrentclass = currstudent.getEnrolledCourses().size(); 
//		String classNames[]=courses.getArray();
//		Integer classSect[]=courses.getSect();
//		Integer[] arrayCourses=null;
//		for(int i=0;i<classNames.length;i++) {
//			arrayCourses[i]=courseRepo.findCourse(classNames[i], classSect[i]);
//		}
//		
//		int i = 0;
//		boolean duplicate = false;
//		while(i<classNames.length) {
//
//			duplicate = false;
//			
//			for(int j=0;j<sizeofcurrentclass;j++) {
//
//				if(currstudent.getEnrolledCourses().get(j).getCourse_id()==arrayCourses[i]) {
//					duplicate = true;
//					i++;
//					break;
//				}
//			}
//			
//			if(!duplicate) {
//				currstudent.getEnrolledCourses().add(courseRepo.findById(arrayCourses[i]).get());
//			}
//			i++;
//		}
//
//
//		studentRepo.save(currstudent);


	String s;

	@GetMapping(path = "/students/matchbyclass/{user}/{courseid}") //for now checks all courses student has, returns all students common with first course
	public String[] matchbyclass(@PathVariable("user") String user,@PathVariable("courseid") Integer courseid) {

		Students stu = studentRepo.findByUser(user);
		List<Course> courses = stu.getEnrolledCourses();

		//int i = courses.size();
		Integer[] arrcourses = new Integer[courses.size()];	//an array of IDs of all courses stu is enrolled in.
		for(int i=0;i<courses.size();i++) {
			Integer list = courses.get(i).getCourse_id();
			arrcourses[i] = list;

		}

		List<Students> students = courseRepo.findById(courseid).get().getStudentList(); //list of students taking a specific course

		String[] arrstudents = new String[students.size()-1];
		int studListSize=students.size();
		int i=0,j=0;
		while(j<studListSize-1) {	
			if(user.equals(students.get(i).getUser())) i++;
			arrstudents[j] = students.get(i).getName();
			i++;
			j++;
		}

		return arrstudents;

	}
	@GetMapping(path = "/students/buddymatch/{user}") //for now checks all courses student has, returns all students common with the courses
	public List<String> buddyMatch(@PathVariable("user") String user) {

		Students stu = studentRepo.findByUser(user);
		List<Course> courses = stu.getEnrolledCourses();

		Integer[] arrcourses = new Integer[courses.size()];	//an array of IDs of all courses stu is enrolled in.
		List<Students> buddies= new ArrayList<Students>();
		List<String> buddiesNames= new ArrayList<String>();
		List<Students> buddiesByDorm=dormMatch(user);
		

		List<Students> homies=new ArrayList<>();
		List<String> realhomies=new ArrayList<>();
		for(int i=0;i<courses.size();i++) {
			Integer ID = courses.get(i).getCourse_id();
			arrcourses[i] = ID;
			List<Students> studbyCRS = courseRepo.findById(ID).get().getStudentList();
			for(Students stud:studbyCRS) {
				if(!buddies.contains(stud)&&!stud.equals(stu)) {
					buddies.add(stud);
					buddiesNames.add(stud.getName());
				}
			}
		}
		for(Students stud:buddies) {
			if(buddiesByDorm.contains(stud)) {
				homies.add(stud);
				realhomies.add(stud.getName());
			}
			
		}
		
		return realhomies;
	}
	
	@GetMapping(path = "/students/dormMatch/{user}") //for now checks all courses student has, returns all students common with the courses
	public List<Students> dormMatch(@PathVariable("user") String user) {

		Students stu = studentRepo.findByUser(user);
		String currDorm=stu.getdorm_loc();
		List<Students> everyone=studentRepo.findBydorm_loc(currDorm);
		List<Students> StudbyDorm=new ArrayList<Students>();
		for(Students tempStud: everyone) {
			if(tempStud.getdorm_loc().equals(currDorm)) {
				StudbyDorm.add(tempStud);
			}
		}
		return StudbyDorm;
	}
	
	@GetMapping(path = "/students/{user}")
	public Students getStudInfo(@PathVariable String user) {
		return studentRepo.findByUser(user);
	}
	@GetMapping(path = "/students/enrolled/{user}")
	public List<Course> tester2(@PathVariable String user) {

		Students stud = studentRepo.findByUser(user);
		
		return stud.getEnrolledCourses();
	}
	@GetMapping(path = "/students/chatgroups/{user}")
	public List<String> populateChatters(@PathVariable String user) {
		Students stud = studentRepo.findByUser(user);
		List<String> retList=new ArrayList<String>();
		for(int i=0;i<stud.getEnrolledCourses().size();i++) {
			retList.add(stud.getEnrolledCourses().get(i).getCourse_name());
		}
		retList.add(stud.getdorm_loc());
		return retList;
	}

}


