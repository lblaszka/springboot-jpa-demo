package com.github.lblaszka.springbootjpademo;

import com.github.lblaszka.springbootjpademo.domain.Book;
import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.BookRepository;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootJpaDemoApplication implements CommandLineRunner
{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaDemoApplication.class, args);
	}

	@Autowired
	BookRepository bookRepository;
	@Autowired
	LibraryRepository libraryRepository;

	@Override
	public void run( String... args ) throws Exception
	{
		Library library = new Library( 0L, "Witam", null );
		library = libraryRepository.save( library );

		Book book = new Book( 0L, "WitamK",library );
		bookRepository.save( book );
	}
}
