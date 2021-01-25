package com.project.websocket.chat.Business;

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

import com.project.websocket.chat.DataAccess.IUserDal;
import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;
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
	public User saveUser(User user) {
		return userDal.saveUser(user);
	}

	@Override
	public User updateUser(User user) {
		//Bu servis henüz bitmedi.
		User existUser = userDal.findById(user.getId());
		userDal.saveUser(existUser);
		return null;
	}

	@Override
	public User deleteUserById(User user) {
		return userDal.deleteUserById(user);
	}

	@Override
	public List<User> userSearch(User user) {
		// Bu servis henüz bitmedi..
		return null;
	}

	@Override
	public List<User> findAll() {
		return userDal.findAll();
	}

	@Override
	public List<User> customSelect(User user) {
		// Bu servis henüz bitmedi..
		return null;
	}

	@Override
	public List<User> saveAllUser(List<User> users) {
		return userDal.saveAllUser(users);
	}

	@Override
	public String login(LoginDto loginDto) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = findByUserName(loginDto.getUserName());
		if(user == null )  throw new Exception("Kullanıcı adı ve şifrenizi kontrol ediniz.");
		
		boolean isPasswordMatch = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
		if(!isPasswordMatch) throw new Exception("Geçersiz kullanıcı adı yada şifre");
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUserName(), user.getPassword()));
		} catch(Exception e) {
			throw new Exception("oturum açılırken bir sorun oluştur");
		}
		
		String result = jwtUtil.generateToken(loginDto.getUserName()) == null ? "kullanıcı yada şifre yanlış" : jwtUtil.generateToken(loginDto.getUserName());
		
		return result;
	}

	@Override
	public User register(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Date date= new Date();
		String password = user.getPassword();
		String hashPassword = passwordEncoder.encode(password);
		user.setPassword(hashPassword);
		user.setCreatedAt(new Timestamp(date.getTime()));
		return userDal.saveUser(user);
	}
	
	@Override
	public User findByUserName(String userName) {
		return userDal.findByUserName(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = findByUserName(userName);
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), new ArrayList<>());
	}

}
