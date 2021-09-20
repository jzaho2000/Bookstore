package com.aho.bookstore.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aho.bookstore.domain.Book;
import com.aho.bookstore.domain.BookRepository;
import com.aho.bookstore.domain.Category;
import com.aho.bookstore.domain.CategoryRepository;

@Controller
public class CategoryController {
	@Autowired BookRepository bookRepository;
	@Autowired CategoryRepository categoryRepository;
	
	private String errors = "";
	private String notice = "";
	
	
	@RequestMapping(value="/categorylist")
	public void getCategoryList(Model model) {
		
		model.addAttribute("notice", this.notice);
		model.addAttribute("errors", this.errors);
		model.addAttribute("addcategory", new Category());
		model.addAttribute("categories", categoryRepository.findAll());
		
		this.errors = "";
		this.notice = "";
	}
	
	
	@RequestMapping(value="/addcategory", method=RequestMethod.POST)
    public String savePost(@Valid Category category, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
    		this.errors = "Validation error(s). ";
    		return "redirect:categorylist";
    	}
    	
    	if (category == null || category.getName() == null || category.getName().trim().equals("") ||  category.getName().toLowerCase().equals("(empty)") ) {
    		this.errors = "Validation error(s). ";
    		return "redirect:categorylist";
    	}
    	
    	this.notice = "New category succesfully added to database!";
        categoryRepository.save(category);
        return "redirect:categorylist";
    }  
	
	
	@RequestMapping(value = "/deletecategory/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long categoryId, Model model) {
		
		if (categoryId == null) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		Optional<Category> list= categoryRepository.findById(categoryId);
		if ( list.isEmpty() == false && list.get() != null && list.get().getName().equals("(empty)") ) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		
		
		List<Book> blist = list.get().getBookList();
		if (blist != null && blist.size()>0) {
			
			Category c = categoryRepository.findByName("(empty)").get(0);
			Book b;
			
			for (int i=0; i<blist.size(); i++) {
				b = blist.get(i);
				b.setCategory(c);
				c.getBookList().add(b);
			}
			
			
		}
		
		
    	categoryRepository.deleteById(categoryId);
    	
    	this.notice = "Category were succesfully deleted from database.";
        return "redirect:../categorylist";
    }     
	

	@RequestMapping(value = "/editcategory/{id}", method = RequestMethod.GET)
    public String editBookGET(@PathVariable("id") Long categoryId, Model model) {
		
		Optional<Category> list= categoryRepository.findById(categoryId);
		
		if ( list.isEmpty() == false && list.get() != null && list.get().getName().equals("(empty)") ) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		model.addAttribute("errors", this.errors);
    	model.addAttribute("category", categoryRepository.findById(categoryId));
    	return "editcategory";
    }   

	@RequestMapping(value = "/editcat", method = RequestMethod.POST)
    public String editBookPOST(@Valid Category category, BindingResult bindingResult){
		
		
    	if (bindingResult.hasErrors()) {
    		this.errors = "Validation error(s). ";
    		return "redirect:/editcategory/" + category.getCategoryid().longValue();
    	}
    	
    	if (category == null) {
    		this.errors = "Validation error(s). ";
    		return "redirect:/categorylist";
    	}
    	
    	if (category.getCategoryid()==null || category.getName() == null || category.getName().trim().equals("")==true || category.getName().toLowerCase().equals("(empty)")==true ) {
    		this.errors = "Validation error(s). ";
    		return "redirect:/editcategory/" + category.getCategoryid().longValue();
    		
    	}
    	
    	Category c = categoryRepository.findByName("(empty)").get(0);
		if ( category.getCategoryid().longValue() == c.getCategoryid().longValue() ) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		
		
		categoryRepository.save(category);
    	this.notice = "Category editing were success!";
        return "redirect:categorylist";
    	
    }   
	
	
}
