package com.project.websocket.chat.DataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.websocket.chat.entities.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String userName);
	
	List<User> findByUserNameContaining(String userName);
	
	List<User> findByUserNameAndEmail(String userName, String email);
}
