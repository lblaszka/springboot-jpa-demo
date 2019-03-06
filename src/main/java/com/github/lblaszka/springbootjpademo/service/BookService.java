package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import com.github.lblaszka.springbootjpademo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.lblaszka.springbootjpademo.config.BookConfig.BOOK_NAME_NIM_LENGTH;

@Service
public class BookService
{
    @Autowired
    BookRepository bookRepository;

    public List<Book> getAll()
    {
        return bookRepository.findAll();
    }

    public ResponseEntity<Book> getById( Long bookId )
    {
        Optional<Book> bookOptional = bookRepository.findById( bookId );

        if( bookOptional.isPresent() )
            return new ResponseEntity<>( bookOptional.get(), HttpStatus.OK );

        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }

    public ResponseEntity<Book> add( Book book )
    {
        if( book.getName() == null || book.getName().length() < BOOK_NAME_NIM_LENGTH )
            return new ResponseEntity<>( HttpStatus.CONFLICT );

        if( book.getLibrary() == null )
            return new ResponseEntity<>( HttpStatus.CONFLICT );

        return new ResponseEntity<>( bookRepository.save(  book ), HttpStatus.OK );
    }
}
