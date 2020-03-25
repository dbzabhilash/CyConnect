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

import com.example.demo.controllers.*;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import com.example.demo.services.*;

public class TestInfo_pageService {

	
	@InjectMocks
	info_pageService infoService;

	@Mock
	info_pageRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getinfoListbyType() {
		
		List<info_page> listDorm = new ArrayList<info_page>();
		List<info_page> listStudy = new ArrayList<info_page>();
		
		//Dorm location
		info_page info1 = new info_page(7,"Buchanan Hall", 43.9983743,-93.9982645,2);
		info_page info2 = new info_page(8,"Friley Hall", 42.9983743,-96.9982645,2);
		info_page info3 = new info_page(9,"Wallace Hall", 41.9983743,-91.9982645,2);
		
		//Study location
		info_page info4 = new info_page(2,"Memorial Union", 42.9876574,-92.3463456,0);
		info_page info5 = new info_page(1,"ISU Library", 40.9346574,-91.34567775,0);
		
		listDorm.add(info1);
		listDorm.add(info2);
		listDorm.add(info3);
		
		listStudy.add(info4);
		listStudy.add(info5);
		
		when(repo.findByTypeofPlace(0)).thenReturn(listStudy); //mock repository to have the dorm locations
		when(repo.findByTypeofPlace(2)).thenReturn(listDorm);
		
		List<info_page> DormlocList = infoService.getInfobyType(2);
		List<info_page> StudylocList = infoService.getInfobyType(0);
		
		assertEquals(3, DormlocList.size());
		assertEquals(2, StudylocList.size());
	}
	
}
