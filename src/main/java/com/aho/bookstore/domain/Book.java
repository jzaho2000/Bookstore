package com.aho.bookstore.domain;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;


@Entity
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long bookid;
	
	@NotNull
	private String title;
	
	@NotNull
	//@Size(min=2, max=90)
	private String author;
	
	@NotNull
	private String isbn;
	
	
	//@NotNull
	private Integer year;
	
	//@ManyToOne
	//@JoinColumn(name = "categoryid")
	//private Category category;

	
	//private double price;
	//@NotNull
	private Long categoryid;

	
	public Book() {
		
	}
	
	
	public Book(String title, String author, Integer year, String isbn) {	
		this.title = title;
		this.author = author;
		this.year = year;
		this.isbn = isbn;
		//this.price = price;
	}
	
	public Book(String title, String author, Integer year, String isbn, long categoryid) {	
		this.title = title;
		this.author = author;
		this.year = year;
		this.isbn = isbn;
		this.categoryid = categoryid;
		//this.price = price;
	}
	
	
	/*
	public Book(String title, String author, Integer year, String isbn, Category category) {	
		this.title = title;
		this.author = author;
		this.year = year;
		this.isbn = isbn;
		this.category = category;
	}
	*/

	
	public Long getBookid() {
		return this.bookid;
	}
	
	public void setBookid(Long id) {
		this.bookid = id;
	}
	
	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	

	/*
	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}
	*/

	public Long getCategoryid() {
		return categoryid;
	}


	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	

	@Override
	public String toString() {
		
		return "Book [id=" + bookid + ", title=" + title + ", author=" + author + ", year=" + year.intValue() + 
				", isbn=" + isbn + "]";
				//", category=" + (this.category == null ? "NULL" : this.category.getName()) + "]";
	}



	
	
	
}
