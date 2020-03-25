package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Students;
import com.example.demo.repositories.StudentsRepository;

@Service
public class StudentService {

	@Autowired
	private StudentsRepository stu_repo;

	public Students getStudentsbyUsername(String user) {
		return stu_repo.findByUser(user);
	}

	public List<Students> getStudentList() {
		return stu_repo.findAll();
	}

	
}
