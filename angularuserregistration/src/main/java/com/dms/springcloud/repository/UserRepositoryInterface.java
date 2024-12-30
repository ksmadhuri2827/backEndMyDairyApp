package com.dms.springcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;

import com.dms.springcloud.entity.User;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
	
    @Query(value= "select * from users where username=:username",nativeQuery = true)
	public User findByUsername(String username);
}
