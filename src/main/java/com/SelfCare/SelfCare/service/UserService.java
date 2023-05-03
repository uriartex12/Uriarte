package com.SelfCare.SelfCare.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
	
	
	public User singIn(User params)throws Exception{
		try {
			
		User user=userRepositoryI.singIn(params.getUsername(),params.getPassword());
		if(user.getStatus()==0)throw new Exception("token is expired");
		return user;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

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
					.token(hash())
					.email(params.getEmail())
					.status(1)
					.build();
			User user=userRepositoryI.save(u);
			
			return user;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	
	private String hash() throws Exception {
		byte[] aesKey = new byte[16]; // 16 bytes = 128 bits
		String salt = getSalt();
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(aesKey);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	private String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}
	
}
