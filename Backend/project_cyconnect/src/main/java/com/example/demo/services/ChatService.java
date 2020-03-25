package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.chat;
import com.example.demo.models.Students;
import com.example.demo.repositories.chatRepository;

@Service
public class ChatService {
	
	@Autowired
	private chatRepository chatRepository;
	
	public List<chat> getchatList() {
		return chatRepository.findAll();
	}

}
