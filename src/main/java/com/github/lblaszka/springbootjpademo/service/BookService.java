package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import com.github.lblaszka.springbootjpademo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService
{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookValidator bookValidator;

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
        if( bookValidator.check( book ) == false )
            return new ResponseEntity<>( HttpStatus.CONFLICT );

        try
        {
            book.setId( 0L );
            return new ResponseEntity<>( bookRepository.save(  book ), HttpStatus.OK );
        }
        catch ( Exception ex )
        {
            System.err.println( ex.getMessage() );
            return new ResponseEntity<>( HttpStatus.CONFLICT );
        }

    }

    public ResponseEntity delete( Book book )
    {
        try
        {
            bookRepository.delete( book );
        }
        catch ( Exception ex )
        {
            System.err.println( ex.getMessage() );
            return new ResponseEntity( HttpStatus.CONFLICT );
        }
        return new ResponseEntity( HttpStatus.OK );
    }

    public ResponseEntity deleteById( Long bookId )
    {
        try
        {
            bookRepository.deleteById( bookId );
        }
        catch ( Exception ex )
        {
            System.err.println( ex.getMessage() );
            return new ResponseEntity( HttpStatus.CONFLICT );
        }
        return new ResponseEntity( HttpStatus.OK );
    }

    public ResponseEntity<Book> update( Book book )
    {
        if( bookValidator.checkUpdate( book ) == false  )
            return new ResponseEntity<>( HttpStatus.CONFLICT );
        if( bookRepository.findById( book.getId() ).isPresent() )
        {
            try
            {
                return new ResponseEntity<>( bookRepository.save( book ), HttpStatus.OK );
            }
            catch ( Exception exc )
            {
                System.err.println( exc.getMessage() );
            }
        }

        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }

    public ResponseEntity<Book> update( Book book, Long id )
    {
        book.setId( id );
        return update( book );
    }
}
