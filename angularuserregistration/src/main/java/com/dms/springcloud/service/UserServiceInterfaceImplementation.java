package com.dms.springcloud.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.springcloud.entity.User;
import com.dms.springcloud.repository.UserRepositoryInterface;

@Service
public class UserServiceInterfaceImplementation implements UserServiceInterface {
	
	@Autowired
	private UserRepositoryInterface userRepositoryInterface;

	@Override
	public User saveUser(User user) {
		return userRepositoryInterface.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepositoryInterface.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepositoryInterface.delete(user);
	}

	@Override
	public User findById(long id) {
		return userRepositoryInterface.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		return userRepositoryInterface.findAll();
	}
	@Override
	public User findByUsername(String username) {
		
		return userRepositoryInterface.findByUsername(username);
	}

}
