package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.Login;
import com.example.demo.models.Students;

public interface StudentsRepository extends JpaRepository<Students,Integer> {

	List<Students> findAll();
	@SuppressWarnings("unchecked")
	Students save(Students student);
	
	@Query(value = "SELECT * FROM students WHERE user = ?1", nativeQuery = true)
	Students findByUser(String user);
	
	@Query(value = "SELECT * FROM students WHERE dorm_loc = ?1", nativeQuery = true)
	List<Students> findBydorm_loc(String dorm_loc);
//	@Query(value = "SELECT student_id FROM enrolled WHERE course_id = ", nativeQuery = true)
//	Integer findByCourse_id(Integer i);
	

	
	@Query(value = "SELECT chat_id FROM chat_rooms WHERE student_id = ", nativeQuery = true)
	Integer findChatByStud_id(Integer stud_id);
}
