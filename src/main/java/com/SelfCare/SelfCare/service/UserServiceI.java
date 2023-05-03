package com.SelfCare.SelfCare.service;

import com.SelfCare.SelfCare.entity.User;

public interface UserServiceI {
	public User save(User params)throws Exception;
	public User singIn(User params)throws Exception;
}
