package com.aho.bookstore.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aho.bookstore.domain.User;
import com.aho.bookstore.domain.UserDAO;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	
	private final UserDAO userDAO;
	@Autowired
	public UserDetailServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User curruser = userDAO.findByUsername(username);
			UserDetails user = new org.springframework.security.core.userdetails.User(username,
			curruser.getPasswordHash(),
			
			AuthorityUtils.createAuthorityList(curruser.getRole()));
			return user;
	}

}
