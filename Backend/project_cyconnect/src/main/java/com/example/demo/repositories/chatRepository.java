package com.example.demo.repositories;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.example.demo.models.Students;
import com.example.demo.models.chat;

@Repository
public interface chatRepository extends JpaRepository<chat,Integer> {
	List<chat> findAll();
	@SuppressWarnings("unchecked")
	chat save(String anychat);
	
	@Query(value = "SELECT * FROM chat WHERE chat_name = ?1", nativeQuery = true)
	chat findByChatName(String chatName);
	
//	@Query(value = "SELECT student_id FROM chat_rooms WHERE chat_id = ", nativeQuery = true)
//	Students findStudByChat_id(Integer i);
	
	@Query(value = "SELECT COUNT(*) FROM chat_rooms WHERE chat_id = ?1", nativeQuery = true)
	Integer countMembers(Integer i);
}
