package com.example.demo.repositories;

import com.example.demo.models.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
	
	List<Course> findAll();
	@Query(value = "SELECT * FROM course WHERE course_name = ?1 AND section = ?2", nativeQuery = true)
	Course findCourse(String course_Name, Integer sect);
	
}
