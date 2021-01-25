package com.project.websocket.chat.DataAccess;

import java.util.List;

import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;

public interface IUserDal {
	
	User findById(int id);
	
	User saveUser(User user);
	
	User updateUser(User user);
	
	User deleteUserById(User user);
		
	List<User> userSearch(User user);
	
	List<User> findAll();
	
	List<User> customSelect(User user);
	
	List<User> saveAllUser(List<User> users);
	
	String login(LoginDto loginDto);	//Login
	
	User register(User user);			//Register
	
	User findByUserName(String userName);
	
}
