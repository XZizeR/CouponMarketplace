package com.coupon.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.exception.InvalidClientTypeException;
import com.coupon.exception.LoginException;
import com.coupon.facade.ClientFacade;
import com.coupon.login.ClientType;
import com.coupon.login.LoginManager;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginManager loginManager;
	@Autowired
	private Map<String, Session> sessionsMap;

//	SIGN-IN
	@PostMapping("/login/{email}/{password}/{type}")
	public String login(@PathVariable String email, @PathVariable String password, @PathVariable ClientType type) {
//		token key
		String token = UUID.randomUUID().toString();
		try {
			ClientFacade facade = loginManager.login(email, password, type);
			Session session = new Session(facade, System.currentTimeMillis());
			sessionsMap.put(token, session);

			return token;
		} catch (InvalidClientTypeException e) {
			return "Error: " + e.getMessage();
		} catch (LoginException e) {
			return "Error: " + e.getMessage();
		}
	}

//	SIGN-OUT
	@PostMapping("/logout/{token}")
	public void logout(@PathVariable String token) {
		sessionsMap.remove(token);
	}

}
