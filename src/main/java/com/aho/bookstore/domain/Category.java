package com.aho.bookstore.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryid;
	
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Book> bookList;
	
	public Category() { }
	
	
	public Category(String name) {
		this.name = name;
	}
	
	public Category(Long id, String name) {
		this.categoryid= id;
		this.name = name;
	}


	public Long getCategoryid() {
		return categoryid;
	}


	public void setCategoryid(Long id) {
		this.categoryid = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Book> getBookList() {
		return bookList;
	}


	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}


	@Override
	public String toString() {
		return "Category [id=" + this.categoryid + ", name=" + this.name + "]";
			//", Category size=" + (this.bookList==null ? 0 : this.bookList.size() ) + "]";
	}
	
	
	
	
	
}
