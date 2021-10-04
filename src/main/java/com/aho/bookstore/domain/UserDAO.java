package com.aho.bookstore.domain;

import java.util.List;

public interface UserDAO {

		
	public void save(User user);
		
	public User findOne(Long id); 
		
	public User findByUsername(String username);

	
		
	
		

		
		

}
