package com.mockito.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.models.Students;
import com.example.demo.repositories.StudentsRepository;
import com.example.demo.services.StudentService;


public class TestStudentsService {

	@InjectMocks
	StudentService stuService;

	@Mock
	StudentsRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getStudentsbyUsernameTest() {
		
		//mock findByUser to return something when john45 is queries
		when(repo.findByUser("john45")).thenReturn(new Students(1,"john45","John Matthew","2100 Welch, Ames","Junior","Computer Engineering","Buchanan Hall"));
		
		Students stud1 = stuService.getStudentsbyUsername("john45");
		
		assertEquals("john45",stud1.getUser());
		assertEquals("John Matthew",stud1.getName());
		assertEquals("Computer Engineering",stud1.getMajor());
		assertEquals("Buchanan Hall",stud1.getdorm_loc());
	}
	
	@Test
	public void getAllStudentsTest() {
		
		List<Students> list = new ArrayList<Students>();
		
		Students stud1 = new Students(1,"john45","John Matthew","2100 Welch, Ames","Junior","Computer Engineering","Buchanan Hall");
		Students stud2 = new Students(2,"bill22","Bill Murray","East Washington","Senior","Computer Science","Wallace Hall");
		
		list.add(stud1);
		list.add(stud2);
		
		when(repo.findAll()).thenReturn(list); //mock repository to have the 2 students made above
		
		List<Students> studList = stuService.getStudentList();
		assertEquals(2, studList.size());
		verify(repo, times(1)).findAll();
	}

	
}
