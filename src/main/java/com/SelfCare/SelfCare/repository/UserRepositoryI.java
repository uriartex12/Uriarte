package com.SelfCare.SelfCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SelfCare.SelfCare.entity.User;

@Repository
public interface UserRepositoryI  extends JpaRepository<User, Integer>{

}