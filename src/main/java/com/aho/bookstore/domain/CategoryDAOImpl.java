package com.aho.bookstore.domain;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import com.aho.bookstore.BookstoreApplication;


@Repository
public class CategoryDAOImpl  implements CategoryDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public void save(Category category) {
		
		String sql;
		Object[] parameters;
		RowMapper<Category> mapper  = new CategoryRowMapper();;
		List<Category> search_values = null;
		
		if ( category== null || category.getName().trim().equals("") || category.getName().trim().toLowerCase().equals("(empty)") )
			return;
		
		if ( category.getCategoryid() != null ) {
			sql = "select * from category where categoryid=?";
			parameters = new Object[] { category.getCategoryid().longValue() };
			search_values = jdbcTemplate.query(sql, parameters, mapper);
		}
		
		if (search_values == null || search_values.size()<=0) {
		
			sql = "insert into category(name) values(?)";
			parameters = new Object[] { category.getName() };
			jdbcTemplate.update(sql, parameters);
		} else if (search_values != null && search_values.size() == 1){
			sql = "update category set name=? where categoryid=?";
			parameters = new Object[] { category.getName(), category.getCategoryid().longValue() };
			jdbcTemplate.update(sql, parameters);
			
		}

	}
	

	
	
	public Category findOne(Long id) {
		
		long lid = id.longValue();
		
		String sql = "select categoryid, name from category where categoryid = ?";
		Object[] parameters = new Object[] { lid };
		RowMapper<Category> mapper = new CategoryRowMapper();
		Category category = jdbcTemplate.queryForObject(sql, parameters, mapper);
		
		/*
		sql = "select bookid,title,author,year,isbn, 0 AS categoryid, '' AS name " + 
		      "from book " +
			  "where categoryid = ?";
		parameters = new Object[] { lid };
		RowMapper<Book> bookMapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, parameters, bookMapper);
		
		if (books != null) {
			//String text = "findOne(" + books.size() + "): ";
			for (int i=0; i<books.size(); i++) {
				books.get(i).setCategory(category);
				//text += books.get(i).toString();
			}
			
			//log.info(text);
			
			category.setBooklist(books);
		} else {
			category.setBooklist( new ArrayList<Book>() );
			log.info("findOne(0): is empty");
		}
		*/
		
		return category;
	}
	
	public Category findByName(String name) {
		String sql = "select categoryid, name from category where name like ?";
		Object[] parameters = new Object[] { "%" + name + "%" };
		RowMapper<Category> mapper = new CategoryRowMapper();
		Category category = jdbcTemplate.queryForObject(sql, parameters, mapper);
		
		/*
		sql = "select bookid,title,author,year,isbn, 0 AS categoryid, '' AS name " + 
			      "from book " +
				  "where categoryid = ?";
		parameters = new Object[] { category.getCategoryid().longValue() };
		RowMapper<Book> bookMapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, parameters, bookMapper);
		
		if (books != null) {
			//String text = "findByName: ";
			for (int i=0; i<books.size(); i++) {
				books.get(i).setCategory(category);
				//text += books.get(i).toString();
			}
			
			//log.info(text);
			
			category.setBooklist(books);
		} else {
			category.setBooklist( new ArrayList<Book>() );
		}
		*/
		
		return category;
	}
	
	
	public void deleteById(Long id) {
		long lid = id.longValue();
		String sql = "select categoryid, name from category where name = '(empty)'";
		RowMapper<Category> mapper = new CategoryRowMapper();
		Category empty = jdbcTemplate.queryForObject(sql, mapper);
		
		if (lid == empty.getCategoryid())
			return;
		
		//sql = "update book set categoryid=" + empty.getCategoryid() + " WHERE categoryid=" + lid;
		//jdbcTemplate.execute(sql);
		
		sql ="update book set categoryid=? where categoryid=?";
		Object[] parameters = new Object[] { empty.getCategoryid().longValue(), id.longValue() };
		jdbcTemplate.update(sql, parameters);
		
		sql = "delete from category WHERE categoryid=?";
		parameters = new Object[] { id.longValue() };
		jdbcTemplate.update(sql, parameters);
		
	}
	
	
	public List<Book> findBooks(int id) {
		String sql = "select A.bookid, A.author, A.title, A.year, A.isbn, A.categoryid, B.name from book as A join category as B on A.categoryid=B.categoryid where category.categoryid = ?";
		Object[] parameters = new Object[] { id };
		RowMapper<Book> mapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, parameters, mapper);

		return books;
	}
	

	public List<Category> findAll() {

		String sql = "select categoryid, name from category";
		RowMapper<Category> mapper = new CategoryRowMapper();
		List<Category> categories = jdbcTemplate.query(sql, mapper);

		return categories;
	}


}

