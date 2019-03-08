package com.hust.polls.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.polls.model.User;
import com.hust.polls.repository.UserRepository;
import com.hust.polls.security.UserPrincipal;
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByUsernameOrEmail(username,username).orElseThrow(()->
			new UsernameNotFoundException("User not found with username or email :"+username)
				);
		
		
		return UserPrincipal.create(user);
	}
	//This method is used by JWT AuthenticationFilter
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user=userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
		return UserPrincipal.create(user);
	}
}
