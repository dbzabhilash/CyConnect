package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.*;
import com.example.demo.repositories.*;

@Service
public class info_pageService {

	@Autowired
	private info_pageRepository info_pageRepository;
	
	public List<info_page> getinfo_pageList() {
		return info_pageRepository.findAll();
	}
	
	public List<info_page> getInfobyType(int i) {
		
		return info_pageRepository.findByTypeofPlace(i);
		
	}
}
