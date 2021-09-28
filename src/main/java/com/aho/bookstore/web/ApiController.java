package com.aho.bookstore.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aho.bookstore.domain.Book;
import com.aho.bookstore.domain.BookDAO;
import com.aho.bookstore.domain.Category;
import com.aho.bookstore.domain.CategoryDAO;

@Controller
public class ApiController {
	
	@Autowired BookDAO bookDAO;
	@Autowired CategoryDAO categoryDAO;
	

	@RequestMapping(value="/api", method=RequestMethod.GET)
	@ResponseBody
	public String getBooklist(
			@RequestParam(name="bookid", defaultValue="0") Long bookid,
			@RequestParam(name="title", defaultValue="") String title,
			@RequestParam(name="author", defaultValue="") String author,
			@RequestParam(name="category", defaultValue="") String categoryStr
			) {
		
		
		
		
		if ( bookid>0 ) {
			Book book = bookDAO.findOne(bookid);
			ArrayList<Book> list = new ArrayList<Book>();
			list.add(book);
			return booklistToJson(list);

		} else if ( !title.trim().equals("") ) {
			List<Book> books = bookDAO.findTitle(title);
			return booklistToJson(books);
			
		} else if ( !author.trim().equals("") ) {
			List<Book> books = bookDAO.findAuthor(author);
			return booklistToJson(books);
			
		} else if ( !categoryStr.trim().equals("") ) {
			Category category = categoryDAO.findByName(categoryStr);
			if (category == null || category.getCategoryid() == null )
				return "[]";
			
			List<Book> books = bookDAO.findCategory(category.getCategoryid());
			return booklistToJson(books);
			
			
		}
		
		List<Book> books =  bookDAO.findAll();
		return booklistToJson(books);
		
		
	}
	
	
	private String booklistToJson(List<Book> books) {
		if (books == null || books.size()<=0)
			return "[]";
		List<Category> category = categoryDAO.findAll();
		
		Book b;
		Category c;
		String text = "[";
		for (int i=0;i<books.size(); i++) {
			b = books.get(i);
			
	
			
			text += "{bookid: " +b.getBookid()  +", title: \"" + b.getTitle() + "\", author: \"" + b.getAuthor() + "\", year: " 
			+ b.getYear() + ", isbn: \"" + b.getIsbn() + "\", categoryid: " + b.getCategoryid();
			
			for (int j=0;j<category.size(); j++) {
				if (category.get(j).getCategoryid() == b.getCategoryid())
					text += ", category: " + category.get(j).getName();
				
			}
			
			if (i<books.size()-1)
				text += "},\n";
			else
				text += "}";

		}
	
		text += "]";
		return text;
	}
	
	
	
	
	
	
	
}
