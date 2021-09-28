package com.aho.bookstore.web;

//import java.util.List;
//import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aho.bookstore.domain.Category;
import com.aho.bookstore.domain.CategoryDAO;





@Controller
public class CategoryController {
	//@Autowired BookDAO bookDAO;
	@Autowired CategoryDAO categoryDAO;
	
	private String errors = "";
	private String notice = "";
	
	
	@RequestMapping(value="/categorylist")
	public void getCategoryList(Model model) {
		
		model.addAttribute("notice", this.notice);
		model.addAttribute("errors", this.errors);
		model.addAttribute("addcategory", new Category());
		model.addAttribute("categories", categoryDAO.findAll());
		
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
        categoryDAO.save(category);
        return "redirect:categorylist";
    }  
	
	
	@RequestMapping(value = "/deletecategory/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long categoryId, Model model) {
		
		if (categoryId == null) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		Category category = categoryDAO.findOne(categoryId); 
		if ( category == null || category.getName().equals("(empty)") ) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		
    	categoryDAO.deleteById(categoryId);
    	
    	this.notice = "Category were succesfully deleted from database.";
        return "redirect:../categorylist";
    }     
	

	@RequestMapping(value = "/editcategory/{id}", method = RequestMethod.GET)
    public String editBookGET(@PathVariable("id") Long categoryId, Model model) {
		
		Category category = categoryDAO.findOne(categoryId); 
		
		if ( category == null || category.getName().equals("(empty)") ) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		model.addAttribute("errors", this.errors);
    	model.addAttribute("category", category);
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
    	
    	Category c = categoryDAO.findByName("(empty)");
		if ( category.getCategoryid().longValue() == c.getCategoryid().longValue() ) {
			this.errors = "Validation error!";
			return "redirect:../categorylist";
		}
		
		
		
		categoryDAO.save(category);
    	this.notice = "Category editing were success!";
        return "redirect:categorylist";
    	
    }   
	
	
}
