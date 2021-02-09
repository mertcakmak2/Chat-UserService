package com.project.websocket.chat.DataAccess;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;
import com.project.websocket.chat.entities.UserImage;

public interface IUserDal {

	User findById(int id);

	User updateUser(User user);

	User deleteUser(User user);

	List<User> userSearch(String userName);

	List<User> findAll();

	List<User> customSelect(User user); // Dynamic User Query

	User register(User user) throws Exception; // Register

	User findByUserName(String userName);

	User uploadImage(MultipartFile file, int userId) throws IOException;

	User deleteImage(UserImage userImage) throws IOException;

}
