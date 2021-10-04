package com.aho.bookstore.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void save(User user) {
		if (user==null)
				return;
		
		String sql = "insert into user(username,password,role) values(?,?,?)";
		Object[] parameters = new Object[] { user.getUsername(), user.getPasswordHash(), user.getRole() };
		jdbcTemplate.update(sql, parameters);
		
	}

	@Override
	public User findOne(Long id) {

		String sql = "select id, username, password,role from user where userid = ?";
		Object[] parameters = new Object[] { id };
		RowMapper<User> mapper = new UserRowMapper();
		User user = jdbcTemplate.queryForObject(sql, parameters, mapper);
		
		return user;
	}

	@Override
	public User findByUsername(String username) {
		String sql = "select id, username, password,role from user where username = ?";
		Object[] parameters = new Object[] { username };
		RowMapper<User> mapper = new UserRowMapper();
		//User user = jdbcTemplate.queryForObject(sql, parameters, mapper);
		List<User> users = jdbcTemplate.query(sql, parameters, mapper);
		
		if (users.size()==1)
			return users.get(0);
		
		return  new User(Long.parseLong("" + 0), "", "", "USER") ;
		
	}

}
