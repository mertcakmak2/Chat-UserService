package com.project.websocket.chat.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;

@Repository
public class UserDal implements IUserDal {
	
	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public User findById(int id) {
		return repository.findById(id).get();
	}

	@Override
	public User saveUser(User user) {
		return repository.save(user);
	}

	@Override
	public User updateUser(User user) {
		//Bu servis henüz bitmedi
		return null;
	}

	@Override
	public User deleteUserById(User user) {
		repository.deleteById(user.getId());
		return user;
	}

	@Override
	public List<User> userSearch(User user) {
		//Bu servis henüz bitmedi..
		return null;
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public List<User> customSelect(User user) {
		// Bu servis henüz bitmedi.
		return null;
	}

	@Override
	public List<User> saveAllUser(List<User> users) {
		return repository.saveAll(users);
	}

	@Override
	public String login(LoginDto loginDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User register(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserName(String userName) {
		return repository.findByUserName(userName);
	}

}
