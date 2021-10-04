package com.aho.bookstore.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper  implements RowMapper<User> {
	
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		User user = new User();
		
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setPasswordHash(rs.getString("password"));
		user.setRole(rs.getString("role"));
		
		return user;
		
	}
}