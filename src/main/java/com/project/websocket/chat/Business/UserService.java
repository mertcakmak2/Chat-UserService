package com.project.websocket.chat.Business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.websocket.chat.DataAccess.IUserDal;
import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;
import com.project.websocket.chat.entities.UserImage;
import com.project.websocket.chat.jwt.JwtUtil;

@Service
public class UserService implements IUserService, UserDetailsService {
	
	@Autowired
	private IUserDal userDal;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public User findById(int id) {
		return userDal.findById(id);
	}

	@Override
	public User updateUser(User user) {
		return userDal.updateUser(user);
	}

	@Override
	public User deleteUser(User user) {
		return userDal.deleteUser(user);
	}

	@Override
	public List<User> userSearch(String userName) {
		return userDal.userSearch(userName);
	}

	@Override
	public List<User> findAll() {
		return userDal.findAll();
	}

	@Override
	public List<User> customSelect(User user) {
		return userDal.customSelect(user);
	}

	@Override
	public String login(LoginDto loginDto) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		User user = userDal.findByUserName(loginDto.getUserName());
		if(user == null )  throw new Exception("Kullanıcı adı ve şifrenizi kontrol ediniz.");
		
		boolean isPasswordMatch = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
		if(!isPasswordMatch) throw new Exception("Geçersiz kullanıcı adı yada şifre");
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUserName(), user.getPassword()));
		} catch(Exception e) {
			throw new Exception("Giriş yapılırken bir sorun oluştu");
		}
		
//		String result = jwtUtil.generateToken(loginDto.getUserName()) == null ? "kullanıcı yada şifre yanlış" : jwtUtil.generateToken(loginDto.getUserName());
		return jwtUtil.generateToken(loginDto.getUserName());
	}

	@Override
	public User register(User user) throws Exception {
		
		//register olmadan önce böyle bir kullanıcının olup olmadığını kontrol et(userName ve maile göre)
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Date date= new Date();
		
		String password = user.getPassword();
		String hashPassword = passwordEncoder.encode(password);
		
		user.setPassword(hashPassword);
		user.setCreatedAt(new Timestamp(date.getTime()));
		user.setUpdatedAt(new Timestamp(date.getTime()));
		return userDal.register(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDal.findByUserName(userName);
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public User uploadImage(MultipartFile file, int userId) throws IOException {
		return userDal.uploadImage(file, userId);
	}

	@Override
	public User deleteImage(UserImage userImage) throws IOException {
		return userDal.deleteImage(userImage);
	}

}
