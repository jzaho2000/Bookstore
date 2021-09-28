package com.aho.bookstore.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aho.bookstore.BookstoreApplication;
import com.aho.bookstore.domain.Book;
import com.aho.bookstore.domain.BookDAO;
import com.aho.bookstore.domain.CategoryDAO;



@Controller
public class BookController {

	@Autowired BookDAO bookDAO;
	@Autowired CategoryDAO categoryDAO;
	
private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	
	private String errors = "";
	private String notice_booklist = "";

	
	
	@RequestMapping(value={"/", "/booklist"})
	//@ResponseBody
	public void getBooklist(Model model) {
		
		model.addAttribute("notice", this.notice_booklist);
		model.addAttribute("books", bookDAO.findAll());
		
		this.errors = "";
		this.notice_booklist = "";
		
	}
	
	
	
	
	

	@RequestMapping(value="/save", method=RequestMethod.POST)
    public String savePost(@Valid Book book, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
    		this.errors = "Validation error(s). ";
    		
    		if (book != null) {
    			
    			List<String> errorList = check_book_data(book.getAuthor(), book.getTitle(), book.getIsbn(), book.getYear() );
    			
    			
    			for (int i=0;i<errorList.size(); i++)
    				this.errors += "\n" + errorList.get(i);
    			
    			
    		} else {
    			this.errors += "You did not send information!";
    		}
    		
    		
    		return "redirect:addbook";
    	}
    	
    	this.notice_booklist = "Book succesfully added to database!";
        bookDAO.save(book);
        return "redirect:booklist";
    }  
	
	/*
	@RequestMapping(value="/save", method=RequestMethod.GET)
    public String saveGet(@RequestParam(name="title", defaultValue="") String title,
			@RequestParam(name="author", defaultValue="") String author,
			@RequestParam(name="year", defaultValue="0") int year,
			@RequestParam(name="isbn", defaultValue="") String isbn,
			@RequestParam(name="categoryid", defaultValue="0") long categoryid,
			Model model ) {
		
		if (title.compareTo("") !=0 || author.compareTo("")!=0) {
			
			List<String> errorList = check_book_data(author, title, isbn, year);
			
			if ( errorList.size()>0 ) {
				
				String returnStr = "Validation error(s). ";
				for (int i=0;i<errorList.size(); i++)
					returnStr += "\n" + errorList.get(i);
				this.errors = returnStr; 
				
				//this.errors = "Validation error. Book where not added to database.";
				
			} else {
				bookDAO.save(new Book(title, author, Integer.valueOf(year), isbn, categoryid));
				this.notice_booklist = "Book succesfully added to database!";
				return "redirect:booklist";
			}
		}
        
        return "redirect:addbook";
    } 
    */
	
	

	
	@RequestMapping(value="/addbook", method=RequestMethod.GET)
	//@ResponseBody
	public void addBook(Model model) {
		
		Book book = new Book();
		
		model.addAttribute("errors", this.errors );
		model.addAttribute("categories", categoryDAO.findAll());
		model.addAttribute("book", book );
		
		this.errors = "";
		//return "BookStore";
	}
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
		
		if (bookId == null) {
			this.errors = "There were error when trying to delete book from database.";
	        return "redirect:../booklist";
		}
		
    	bookDAO.deleteById(bookId);
    	
    	this.notice_booklist = "Book were succesfully deleted from database.";
        return "redirect:../booklist";
    }     
	
	
	@RequestMapping(value = "/editbook/{id}", method = RequestMethod.GET)
    public String editBookGET(@PathVariable("id") Long bookId, Model model) {
		
		if (bookId == null) {
			this.errors = "There were error when trying to find book from database.";
	        return "redirect:../booklist";
		}
		
		
		model.addAttribute("errors", this.errors);
		model.addAttribute("categories", categoryDAO.findAll());
    	model.addAttribute("book", bookDAO.findOne(bookId));
    	return "editbook";
    }  
	

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editBookPOST(@Valid Book book, BindingResult bindingResult){
		
		
    	if (bindingResult.hasErrors()) {
    		
    		this.errors = "Validation error(s). ";
    		
    		if (book != null) {
    			
    			List<String> errorList = check_book_data(book.getAuthor(), book.getTitle(), book.getIsbn(), book.getYear() );
    			
    			for (int i=0;i<errorList.size(); i++)
    				this.errors += "\n" + errorList.get(i);
    			
    			
    		} else {
    			this.errors += "You did not send information!";
    		}
    		
    		return "redirect:/editbook/" + book.getBookid().longValue();
    	}
    	
    	if (book == null)
    		return "redirect:/booklist";
    	
    	List<String> errorList = check_book_data(book.getAuthor(), book.getTitle(), book.getIsbn(), book.getYear() );
    	
    	if (errorList != null && errorList.size()>0) {
    		this.errors = "Validation error(s). ";
    		for (int i=0;i<errorList.size(); i++)
    			this.errors += "\n" + errorList.get(i);
    		
    		if (book.getBookid() != null)
    			return "redirect:/editbook/" + book.getBookid().longValue();
    		
    		return "redirect:/booklist";
    	}
    		
    	this.notice_booklist = "Book editing were success!";
        bookDAO.save(book);
        return "redirect:booklist";
    	
    }   
	
	
	
	

	// ---------- DATA CHECKING METHODS -----------------
	
	
	private List<String> check_book_data(String author, String title, String isbn, int year) {
		return check_book_data(author, title, isbn, Integer.valueOf(year));
	}
	
	private List<String> check_book_data(String author, String title, String isbn, Integer year) {
		
		// I list all the errors to remind myself all the 
		// parameters when doing testing. 
		ArrayList<String> errorList = new ArrayList<String>();
		
		if ( !is_text_ok(author))
			errorList.add("Author value are missig!");
		
		if ( !is_text_ok(title)  ) 
			errorList.add("Title valuea are missing!");
		
		if ( !is_text_ok(isbn) )
			errorList.add("ISBN value are missing!");
		
		if ( !is_year_ok(year) )
			errorList.add("Year are missing or it has invalid values!");
		
		
		
		//if ( !is_price_ok(price) )
		//	errors.add("Price are missing or it has invalid values!");
		
		return errorList;
	}
	
	
	private boolean is_text_ok(String value) {
		if (value == null || value.compareTo("")==0)
			return false;
		
		return true;
	}
	
	private boolean is_year_ok(Integer year) {
		if (year == null)
			return false;
		
		return is_year_ok(year.intValue());
	}
	
	private boolean is_year_ok(int year) {
		if (year<1900 )
			return false;
		
		int current_year = LocalDate.now().getYear();
		if (year>current_year+2)
			return false;
		
		return true;
	}
	
	private boolean is_price_ok(double price) {
		if (price <= 0)
			return false;
		
		// check there are only 2 decimal
		double test = (price - Math.floor(price*100) / 100.0);
		if (test != 0)
			return false;
		
		return true;
	}
	
	
}
