package com.aho.bookstore.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BookRowMapper implements RowMapper<Book> {
	
	
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setBookid(rs.getLong("bookid"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setYear(rs.getInt("year"));
		book.setIsbn(rs.getString("isbn"));
		book.setCategoryid(rs.getLong("categoryid"));
		
		/*
		Category category = new Category();
		category.setCategoryid(rs.getLong("categoryid"));
		category.setName(rs.getString("name"));
		
		book.setCategory(category);
		*/

		return book;
	}
}