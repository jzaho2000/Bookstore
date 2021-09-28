package com.aho.bookstore.domain;


import java.util.List;

public interface BookDAO {
	
	public void save(Book book);

	public Book findOne(Long id); 
	public Book findOne(long id); 

	public List<Book> findAll();
	
	public List<Book> findTitle(String title);
	
	public List<Book> findAuthor(String author);
	
	public List<Book> findCategory(Long categoryid);
	
	public List<Book> findCategory(long categoryid);

	public void deleteById(Long bookId);
}