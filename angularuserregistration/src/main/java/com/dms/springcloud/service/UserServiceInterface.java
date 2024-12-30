package com.dms.springcloud.service;

import java.util.List;



import com.dms.springcloud.entity.User;

public interface UserServiceInterface {
	
	public User saveUser(User user);
	public User updateUser(User user);
	public void deleteUser(User user);
	public User findById(long id);
	public List<User> findAll();
	public User findByUsername(String username);
	
}
