package com.example.demo.Socketing;


import java.io.IOException;
import java.util.HashMap;

import javax.websocket.*;
import javax.websocket.server.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Course;
import com.example.demo.repositories.CourseRepository;

@ServerEndpoint(value = "/websocket/{username}/{groupName}", configurator = CustomConfigurator.class)	//to track who's the sender display the sender
@Component
public class SocketServer {
	private static HashMap<Session, String> sessionUsernameMap = new HashMap<>();
	private static HashMap<String, Session> usernameSessionMap = new HashMap<>();
	private static HashMap<Session, String> sessionGroupMap = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(SocketServer.class);

	@Autowired
	CourseRepository courseRepository;
	
	@OnOpen
	public void onOpen(
			Session session, 
			@PathParam("username") String username, @PathParam("groupName") String groupName) throws IOException 
	{
		logger.info("Entered into Open");
		
		
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);
		sessionGroupMap.put(session, groupName);

		String message="User:" + username + " has Joined the Chat";
		
		Course course = courseRepository.findById(Integer.parseInt(groupName)).get();
		course.incrementConnected();
		courseRepository.save(course);
		
		broadcast(message,groupName);

	}
	@OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String username = sessionUsernameMap.get(session);
    	String groupName=sessionGroupMap.get(session);
    	broadcast(username + ": " + message,groupName);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");
    	
    	String username = sessionUsernameMap.get(session);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);
    	String groupName=sessionGroupMap.get(session);
    	String message= username + " disconnected";
		Course course = courseRepository.findById(Integer.parseInt(groupName)).get();
		course.decrementConnected();
		courseRepository.save(course);
        broadcast(message, groupName);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into WebSocket Error");
    }
    
//	private void sendMessageToPArticularUser(String username, String message) 
//    {	
//    	try {
//    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
//        } catch (IOException e) {
//        	logger.info("Exception: " + e.getMessage().toString());
//            e.printStackTrace();
//        }
//    }
//

//	private static void broadcast(String message, String groupName) throws IOException 
//	{	  
//		sessionUsernameMap.forEach((session, username) -> {
//			synchronized (session) {
//				try {
//					session.getBasicRemote().sendText(message);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	private static void broadcast(String message, String groupName) throws IOException {
		sessionUsernameMap.forEach((session, username) -> {
			synchronized (session) {
				try {
					String tempGroupName = sessionGroupMap.get(session);
					if (tempGroupName.equals(groupName)) {
						session.getBasicRemote().sendText(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}


}
