package com.aho.bookstore.web;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aho.bookstore.model.Book;

@Controller
public class BookController {

	private ArrayList<Book> bookList = new ArrayList<Book>();
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	@ResponseBody
	public String returnIndex(
			@RequestParam(name="title", defaultValue="") String title,
			@RequestParam(name="author", defaultValue="") String author,
			@RequestParam(name="year", defaultValue="0") int year,
			@RequestParam(name="isbn", defaultValue="") String isbn,
			@RequestParam(name="price", defaultValue="0") double price,
			Model model ) {
		
		if (title.compareTo("") !=0 || author.compareTo("")!=0) {
			
			// I list all the errors to remind myself all the 
			// parameters when doing testing. 
			
			ArrayList<String> errors = new ArrayList<String>();
			if ( !is_text_ok(title)  ) 
				errors.add("Title valuea are missing!");
			
			if ( !is_text_ok(author))
				errors.add("Author value are missig!");
			
			if ( !is_year_ok(year) )
				errors.add("Year are missing or it has invalid values!");
			
			// we could also create isbn checkin but we don't
			// have to create it to this course.
			//if ( !is_text_ok(isbn) )
			//	errors.add("ISBN value are missing!");
			
			if ( !is_price_ok(price) )
				errors.add("Price are missing or it has invalid values!");
			
			if ( errors.size()>0 ) {
				
				String returnStr = "";
				for (int i=0;i<errors.size(); i++)
					returnStr += errors.get(i) + "\n";
				return returnStr;
			
			}
			
			bookList.add(new Book(
					title, author, year, isbn, price));
			
		}
		
		String returnStr = "Booklist\n";
		for (int i=0; i<bookList.size(); i++)
			returnStr += bookList.get(i).bookData() + "\n";
		
		
		return returnStr;
	}
	
	
	private boolean is_text_ok(String value) {
		if (value == null || value.compareTo("")==0)
			return false;
		
		return true;
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
