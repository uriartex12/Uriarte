package com.SelfCare.SelfCare.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SelfCare.SelfCare.entity.User;
import com.SelfCare.SelfCare.service.UserServiceI;

@RestController
@RequestMapping("api/user")
public class AuthController {
	 
	 @Autowired
	 private JavaMailSender mailSender;
	 
	 @Autowired
	 UserServiceI userService;
	 
	 public AuthController(UserServiceI userService){
		 this.userService=userService;
	 }
	 

	  @PostMapping("/oauth2/save")
	  public ResponseEntity<?> save(@RequestBody User params) throws Exception {
		  try {
			  User user= userService.save(params);
			  
			  this.sendEmail(user);
			  
			  return new ResponseEntity<>(new HashMap() {{ put("user_id",user.getId()); } }, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new HashMap() {{ put("error",e.getMessage()); } }, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	  }
	  
	  
	  
	  
	  
	  public void sendEmail(User recipientEmail)
		        throws MessagingException, UnsupportedEncodingException {
			MimeMessage message = mailSender.createMimeMessage();              
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		     
		    helper.setFrom("jhon.uriarte@unas.edu.pe", "GoEat");
		    helper.setTo(recipientEmail.getEmail());
		     
		    String subject = "GoEat:Creacion de cuenta";
		     
		    String content = "<p><strong>Hola</strong></p>"
		            + "<p>Estás recibiendo este correo porque hiciste una registro en la pagina de GoEat: </p>"
		            + "<p><strong>USUARIO: "+recipientEmail.getUsername()+"</strong></p>"
		            + "<p><strong>CONTRASEÑA :"+ recipientEmail.getPassword()+"</strong></p>"
		            + "<p>No olvides de jugar para ganar descuentos y regalos sorpresas, "
		            + "solo tendras que acumular puntos.</p>"
		            + "<p>Saludos, GoEat </p>"
		            + "";
		     
		    helper.setSubject(subject);
		     
		    helper.setText(content, true);
		     
		    mailSender.send(message);
		}
	  
}
