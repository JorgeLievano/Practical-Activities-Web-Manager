package com.lievano.cc.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lievano.cc.model.TsscAdmin;
import com.lievano.cc.repository.AdminRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private AdminRepository userRepository;
	
	@Autowired
	public MyCustomUserDetailsService(AdminRepository userRepository) {
		// TODO Auto-generated constructor stub
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TsscAdmin userApp = null;
		userApp=userRepository.findByUser(username);
		
		if (userApp != null) {
			User.UserBuilder builder = User.withUsername(username).password(userApp.getPassword()).roles(userApp.getSuperAdmin().toString());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}