package com.aho.bookstore.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


//import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String title;
	
	@NotNull
	@Size(min=2, max=90)
	private String author;
	
	@NotNull
	private String isbn;
	
	
	//private int year;
	
	@NotNull
	private Integer year;

	
	//private double price;
	

	
	public Book() {
		
	}
	
	
	public Book(String title, String author, Integer year, String isbn) {	
		this.title = title;
		this.author = author;
		this.year = year;
		this.isbn = isbn;
		//this.price = price;
	}

	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
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


	//public double getPrice() {
	//	return price;
	//}


	//public void setPrice(double price) {
	//	this.price = price;
	//}


	public String bookData() {
		return  this.id + ", " + 
				this.title + ", " +
				this.author + ", " + 
				this.year.intValue() + ", " + 
				this.isbn + ", "; // + 
				//this.price;
	}


	@Override
	public String toString() {
		//return "Book [id=" + id + ", title=" + title + ", author=" + author + ", year=" + year + ", isbn=" + isbn
		//		+ ", price=" + price + "]";
		
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", year=" + year.intValue() + ", isbn=" + isbn
				+ "]";
	}
	
	
	
}
