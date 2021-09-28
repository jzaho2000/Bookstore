package com.aho.bookstore;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BookstoreApplication {

	//private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
		
	}
	

	
	//@Bean
	//public CommandLineRunner bookDemo(BookRepository bookRepository, CategoryRepository categoryRepository) {
	//	return (args) -> {
			
			/*
			log.info("Save categories");
			categoryRepository.save(new Category("(empty)"));
			categoryRepository.save(new Category("Animals"));
			categoryRepository.save(new Category("Manuals"));
			*/
			
			
			/*
			log.info("save a couple of students");
			Book(String title, String author, int year, String isbn, double price)
			bookRepository.save(new Book("Leopard Gecko", "Johnson & Johnson", Integer.valueOf(1990), "ISBN-1111", 
					categoryRepository.findByName("Animals").get(0)));
			bookRepository.save(new Book("Leopard Gecko Manual", "Davey & Smith", Integer.valueOf(2020), "ISBN-2222",
					categoryRepository.findByName("Animals").get(0)));
			bookRepository.save(new Book("Dogs & Cats", "Mary Johnson", Integer.valueOf(2015), "ISBN-3333",
					categoryRepository.findByName("Animals").get(0)));
			*/
			
			/*
			log.info("fetch all students");
			
			for (Book book : bookRepository.findAll()) {
				log.info( book.toString() );
			}
			
			for (Category category : categoryRepository.findAll()) {
				log.info( category.toString() );
			}
			*/

		//};
	//}

}
