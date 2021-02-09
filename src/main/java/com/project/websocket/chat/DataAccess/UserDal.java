package com.project.websocket.chat.DataAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

//import com.project.websocket.chat.dto.LoginDto;
import com.project.websocket.chat.entities.User;
import com.project.websocket.chat.entities.UserImage;

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
	public User updateUser(User user) {
		User existUser = repository.findById(user.getId()).get();
		
		Date now= new Date();
		
		existUser.setEmail(user.getEmail());
		existUser.setUserName(user.getUserName());
		existUser.setPassword(existUser.getPassword());	// Hashli şifresi silinmesin diye..
		existUser.setProfilePicUrl(existUser.getProfilePicUrl());
		existUser.setUpdatedAt(new Timestamp(now.getTime()));
		return repository.save(existUser);
	}

	@Override
	public User deleteUser(User user) {
		repository.deleteById(user.getId());
		return user;
	}

	@Override
	public List<User> userSearch(String userName) {
		return repository.findByUserNameContaining(userName);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public List<User> customSelect(User user) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		
		int id = user.getId();
		String email = user.getEmail();
		String userName = user.getUserName();
		
		List<Predicate> searchCriterias = new ArrayList<>();

		if( (email != "" ) && (email != null) ) { 
			searchCriterias.add( criteriaBuilder.like( root.get("email"), "%"+email+"%") );
		}
		
		if( (userName != "" ) && (userName != null) ) { 
			searchCriterias.add( criteriaBuilder.like( root.get("userName"), "%"+userName+"%") );
		}
		
		if(id != 0) {
			searchCriterias.add( criteriaBuilder.equal( root.get("id"), id) );
		}
		
		criteriaQuery.select( root ).where( 
				criteriaBuilder.and( searchCriterias.toArray(new Predicate[searchCriterias.size()]) ));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public User register(User user) throws Exception {
		User newUser = new User();
		try {
			newUser = repository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return newUser;
		
	}

	@Override
	public User findByUserName(String userName) {
		return repository.findByUserName(userName);	// JPA'nın desteklediği find methodu.
	}

	@Override
	public User uploadImage(MultipartFile file, int userId) throws IOException {
		User user = findById(userId);
		String FILE_DIRECTORY = "src/main/resources/images/"+file.getOriginalFilename();
		File image = new File(FILE_DIRECTORY);
		image.createNewFile();
		FileOutputStream fos =new FileOutputStream(image);
		fos.write(file.getBytes());
		fos.close();
		user.setProfilePicUrl(FILE_DIRECTORY);
//		return image.getAbsolutePath()+ "  " + FILE_DIRECTORY;
		return repository.save(user);
	}

	@Override
	public User deleteImage(UserImage userImage) throws IOException {
		var userId = userImage.getUserId();
		var imagePath = userImage.getImagePath();
		User user = findById(userId);

		File image = new File(imagePath);
		Boolean state = image.delete();
		
		user.setProfilePicUrl("");
		return repository.save(user);
	}

}
