package com.SelfCare.SelfCare.service;

import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SelfCare.SelfCare.entity.User;
import com.SelfCare.SelfCare.repository.UserRepositoryI;

@Service
public class UserService implements UserServiceI{
	
	@Autowired
	UserRepositoryI userRepositoryI;

	public User save(User params)throws Exception{
		try {
			
			Random random = new Random();	
			int numeroAleatorio = random.nextInt(1011);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			char letra = (char)(random.nextInt(26) + 'A');
			String letrasAleatorias = "";
			for (int i = 0; i < 5; i++) {
			    char c = (char)(random.nextInt(26) + 'A');
			    letrasAleatorias += c;
			}
			String cadenaAleatoria = String.format("%04d", numeroAleatorio) + letrasAleatorias;
			
			User u= User.builder()
					.username(year+""+numeroAleatorio)
					.password(cadenaAleatoria)
					.email(params.getEmail())
					.build();
			User user=userRepositoryI.save(u);
			
			return user;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
}
