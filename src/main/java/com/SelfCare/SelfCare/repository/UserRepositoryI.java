package com.SelfCare.SelfCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SelfCare.SelfCare.entity.User;

@Repository
public interface UserRepositoryI  extends JpaRepository<User, Integer>{
	
    @Transactional
	@Query(value = "SELECT * FROM users  WHERE username=:username and password=:password",nativeQuery = true)
	public User singIn(String username, String password);
	
}
