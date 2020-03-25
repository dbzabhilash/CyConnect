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

import com.example.demo.models.chat;
import com.example.demo.repositories.chatRepository;
import com.example.demo.services.ChatService;

public class TestChatService {
	
	@InjectMocks
	ChatService chatService;

	@Mock
	chatRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllChatsTest() {
		
		List<chat> list = new ArrayList<chat>();
		
		chat chat1 = new chat(1, "cpre_group");
		chat chat2 = new chat(4, "cpre_group");
		chat chat3 = new chat(12, "coms_group");
		
		list.add(chat1);
		list.add(chat2);
		list.add(chat3);
		when(repo.findAll()).thenReturn(list);
		
		List<chat> newchatlist = chatService.getchatList();
		
		assertEquals(3,newchatlist.size());
		assertEquals("coms_group",newchatlist.get(2).getChat_name());
	}

}
