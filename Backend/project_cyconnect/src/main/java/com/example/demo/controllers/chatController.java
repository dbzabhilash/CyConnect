package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Students;
import com.example.demo.models.chat;
import com.example.demo.repositories.StudentsRepository;
import com.example.demo.repositories.chatRepository;

@RestController
public class chatController {

	@Autowired
	private chatRepository chatRepo;
	@Autowired
	private StudentsRepository studentRepo;
	
	@PostMapping(path = "/validateChat/{groupName}")
	public String validateGroupName(@PathVariable("groupName") String groupName) {
		if(chatRepo.findByChatName(groupName)==null) {
			chat c = new chat();
			c.setChat_name(groupName);
			chatRepo.save(c);
			return "successfull";
		}
		return "Already_Exists";
	}
	@PostMapping(path = "/newChat/{user}/{user2}/{groupName}")
	public String makeNewChat(@PathVariable("user") String user, @PathVariable("user2") String user2,
			@PathVariable("groupName") String groupName) {
		Students inviter = studentRepo.findByUser(user);
		Students invitee = studentRepo.findByUser(user2);		
		chat chatbox=chatRepo.findByChatName(groupName);
		String retVal0="No_action";
		if(!inviter.getchatRooms().contains(chatbox)) {
			inviter.getchatRooms().add(chatbox);			
			retVal0+=" if1";
			studentRepo.save(inviter);
		}
		if(!invitee.getchatRooms().contains(chatbox)) {
			invitee.getchatRooms().add(chatbox);
			retVal0+="if2";
			studentRepo.save(invitee);

		}
		studentRepo.save(inviter);
		return retVal0;
	}
	
	@GetMapping(path="/allChats/{user}")
	public List<chat> populateChats(@PathVariable("user") String user){
		Students currStud=studentRepo.findByUser(user);
		return currStud.getchatRooms();
	}
	
	
}
