package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Author;
import com.github.lblaszka.springbootjpademo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService
{
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorValidator authorValidator;


    public List<Author> getAll()
    {
        return authorRepository.findAll();
    }


    public ResponseEntity<Author> getById( Long id )
    {
        Optional<Author> authorOptional = authorRepository.findById( id );

        if( authorOptional.isPresent() )
        {
            return new ResponseEntity<>( authorOptional.get(), HttpStatus.OK );
        }

        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    public ResponseEntity<Author> delete( Author author )
    {
        return null;
    }


    public ResponseEntity<Author> delete( Long authorId )
    {
        return null;
    }


    public ResponseEntity<Author> add( Author author )
    {
        if( authorValidator.checkToAdd( author ) )
        {
            try
            {
                return new ResponseEntity<>( authorRepository.save( author ), HttpStatus.OK );
            }
            catch ( Exception exc )
            {
                System.err.println( exc.getMessage() );
            }
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    public ResponseEntity<Author> update( Author author )
    {
        if( authorValidator.checkToUpdate( author ) && authorRepository.findById( author.getId() ).isPresent() )
        {
            try
            {
                return new ResponseEntity<>( authorRepository.save( author ), HttpStatus.OK );
            }
            catch ( Exception exc )
            {
                System.err.println( exc.getMessage() );
            }
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }

}
