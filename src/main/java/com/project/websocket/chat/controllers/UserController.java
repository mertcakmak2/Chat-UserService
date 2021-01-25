package com.project.websocket.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.websocket.chat.Business.IUserService;
import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/test")	//5000/api/test
	public String test() {
		return "test";
	}
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginDto) throws Exception {
		return userService.login(loginDto);
	}

}
