package com.project.websocket.chat.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.websocket.chat.Business.IUserService;
import com.project.websocket.chat.DataAccess.IUserRepository;
import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;
import com.project.websocket.chat.entities.UserImage;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
//	@Autowired
//	private EntityManager entityManager;

//	@GetMapping("/test") // 5000/api/test
//	public List<User> test() {
//		
//		String queryStr = "select * from users where user_name LIKE ?1";
//
//		Query query = entityManager.createNativeQuery(queryStr, User.class);
//		query.setParameter(1, "%admin%");
//		
//		List<User> users = query.getResultList();
//		return users;
//	}
	
//	@GetMapping("/{id}")
//	public User findById(@PathVariable int id) {
//		return userService.findById(id);
//	}
	
	@PostMapping("/update")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@PostMapping("/delete")
	public User deleteUser(@RequestBody User user) {
		return userService.deleteUser(user);
	}
	
	@GetMapping("/{username}")
	public List<User> userSearch(@PathVariable String username) {
		return userService.userSearch(username);
	}
	
	@GetMapping("/")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@PostMapping("/custom")
	public List<User> customUser (@RequestBody User user) {
		return userService.customSelect(user);
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginDto) throws Exception {
		return userService.login(loginDto);
	}

	@PostMapping("/register")
	public User register(@RequestBody User user) throws Exception {
		return userService.register(user);
	}

	@PostMapping("/uploadImage")
	public User uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int userId) throws IOException {
		return userService.uploadImage(file, userId);
	}
	
	@PostMapping("/deleteImage")
	public User deleteImage(@RequestBody UserImage userImage) throws IOException {
		return userService.deleteImage(userImage);
	}

}
