package com.example.covidtracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.covidtracker.domain.User;
import com.example.covidtracker.repository.UserRepository;
import com.vaadin.flow.server.VaadinSessionState;
import com.vaadin.flow.spring.scopes.VaadinSessionScope;

@Service
public class UserService {
	@Autowired
	public UserRepository userRepository;

	// stores the user name during the session; the string serves as the key for the
	// value
	public static final String AUTHENTICATED_USER_NAME = "authenticatedUserName";
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void store(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public boolean validateRegistration(User user) {
		boolean flag = false;
		Optional<User> findByUserNameAndEmailAddress = userRepository.findByUserNameAndEmailAddress(user.getUsername(),
				user.getEmail());
		if (findByUserNameAndEmailAddress.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	public boolean validateLogin(User userRequest) {
		boolean flag = true;
		Optional<User> findByUserName = userRepository.findByUserName(userRequest.getUsername());
		if (findByUserName.isEmpty()) {
			flag = false;
		}
		if (findByUserName.isPresent()) {
			if (!passwordEncoder.matches(userRequest.getPassword(), findByUserName.get().getPassword())) {
				flag = false;
			}
			
		}

		return flag;
	}

}
