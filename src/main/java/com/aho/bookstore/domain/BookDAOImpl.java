package com.aho.bookstore.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAOImpl implements BookDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//private static final Logger log = LoggerFactory.getLogger(DatabasedemoApplication.class);

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(Book book) {
		
		List<Book> search_book = null;
		String sql;
		Object[] parameters;
		RowMapper<Book> mapper = new BookRowMapper();
		
		if (book.getBookid() != null) {
			sql = "select bookid, author, title, year, isbn, categoryid " +
		             "from book " + 
		             "where bookid = ?";
			parameters = new Object[] { book.getBookid().longValue() };
			search_book = jdbcTemplate.query(sql, parameters, mapper);
		}
		
		if (search_book == null || search_book.size()<=0) {
			sql = "insert into book(author, title, year, isbn, categoryid) values(?,?,?,?,?)";
			parameters = new Object[] { book.getAuthor(), book.getTitle(), book.getYear(), 
				                        book.getIsbn(), book.getCategoryid().longValue()};
			
			jdbcTemplate.update(sql, parameters);
		} else if (search_book != null && search_book.size() == 1) {
			sql = "update book set author=?, title=?, year=?, isbn=?, categoryid=? where bookid=?";
			parameters = new Object[] { book.getAuthor(), book.getTitle(), book.getYear(), book.getIsbn(), 
					                    book.getCategoryid().longValue(), book.getBookid().longValue() };
			jdbcTemplate.update(sql, parameters);
		}
			
	}
	
	public Book findOne(long id) {
		return findOne(Long.parseLong("" + id));
	}

	public Book findOne(Long id) {
		
		if (id == null)
			return null;
		
		//String sql = "select A.bookid, A.author, A.title, A.year, A.isbn, A.categoryid, B.name from book as A join category as B on A.categoryid=B.categoryid where A.bookid = ?";
		String sql = "select bookid, author, title, year, isbn, categoryid from book where bookid = ?";
		Object[] parameters = new Object[] { id.longValue() };
		RowMapper<Book> mapper = new BookRowMapper();
		Book book = jdbcTemplate.queryForObject(sql, parameters, mapper);
		
		
		return book;

	}

	public List<Book> findAll() {

		//String sql = "select A.bookid, A.author, A.title, A.year, A.isbn, A.categoryid, B.name from book as A join category as B on A.categoryid=B.categoryid";
		String sql = "select bookid, author, title, year, isbn, categoryid from book";
		RowMapper<Book> mapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, mapper);
		
		return books;
	}
	
	
	public List<Book> findTitle(String title) {
		String sql = "select bookid, author, title, year, isbn, categoryid from book where title like ?";
		Object[] parameters = new Object[] { "%" + title + "%" };
		RowMapper<Book> mapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, parameters, mapper);
		
		return books;
	}
	
	public List<Book> findAuthor(String author) {
		String sql = "select bookid, author, title, year, isbn, categoryid from book where author like ?";
		Object[] parameters = new Object[] { "%" + author + "%" };
		RowMapper<Book> mapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, parameters, mapper);
		
		return books;
	}
	
	public List<Book> findCategory(long id) {
		return findCategory(Long.parseLong("" + id));
	}
	
	public List<Book> findCategory(Long id) {
		String sql = "select bookid, author, title, year, isbn, categoryid from book where categoryid=?";
		Object[] parameters = new Object[] { id };
		RowMapper<Book> mapper = new BookRowMapper();
		List<Book> books = jdbcTemplate.query(sql, parameters, mapper);
		
		return books;
	}
	

	
	public void deleteById(Long bookId) {
		
		if (bookId == null)
			return;
		
		String sql = "delete from book where bookid=?";
		Object[] parameters = new Object[] { bookId.longValue() };
		jdbcTemplate.update(sql, parameters);
	}
	
}
