package com.aho.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aho.bookstore.domain.Book;
import com.aho.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
		
	}
	
	@Bean
	public CommandLineRunner bookDemo(BookRepository bookRepository) {
		return (args) -> {
			
			System.out.println("save a couple of students");
			// Book(String title, String author, int year, String isbn, double price)
			bookRepository.save(new Book("Leopard Gecko", "Johnson & Johnson", Integer.valueOf(1990), "ISBN-1111"));
			bookRepository.save(new Book("Leopard Gecko Manual", "Davey & Smith", Integer.valueOf(2020), "ISBN-2222"));
			bookRepository.save(new Book("Dogs & Cats", "Mary Johnson", Integer.valueOf(2015), "ISBN-3333"));
			
			System.out.println("fetch all students");
			//repository.findAll();
			for (Book book : bookRepository.findAll()) {
				System.out.println( book.toString() );
			}

		};
	}

}
