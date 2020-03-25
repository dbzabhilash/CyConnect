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

@ServerEndpoint(value = "/notification/{groupName}", configurator = CustomConfigurator.class)	//to track who's the sender display the sender
@Component
public class NotificationServer {


	@Autowired
	CourseRepository courseRepository;
	
	@OnOpen
	public void onOpen(
			Session session, 
			@PathParam("username") String username, @PathParam("groupName") String groupName) throws IOException 
	{

		Course course = courseRepository.findById(Integer.parseInt(groupName)).get();
		session.getBasicRemote().sendText(course.getConnected() + " users are connected");
		

	}

 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	
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
	

}
