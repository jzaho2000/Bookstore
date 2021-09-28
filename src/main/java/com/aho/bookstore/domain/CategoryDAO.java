package com.aho.bookstore.domain;


import java.util.List;

public interface CategoryDAO {
	
	public void save(Category category);
	
	public Category findOne(Long id); 
	
	public Category findByName(String id); 
	
	public void deleteById(Long id);
	
	public List<Category> findAll();
	
	
}

