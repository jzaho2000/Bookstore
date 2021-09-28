package com.aho.bookstore.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CategoryRowMapper implements RowMapper<Category> {
	
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Category category = new Category();
		
		category.setCategoryid(rs.getLong("categoryid"));
		category.setName(rs.getString("name"));
		
		return category;
		
	}
}
