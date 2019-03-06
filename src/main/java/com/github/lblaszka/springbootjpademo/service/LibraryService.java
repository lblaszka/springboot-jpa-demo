package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.lblaszka.springbootjpademo.config.LibraryConfig.LIBRARY_NAME_MIN_LENGTH;

@Service
public class LibraryService
{
    @Autowired
    LibraryRepository libraryRepository;

    public List<Library> getAll()
    {
        return libraryRepository.findAll();
    }

    public ResponseEntity<Library> getById( Long libraryId )
    {
        Optional<Library> libraryOptional = libraryRepository.findById( libraryId );

        if( libraryOptional.isPresent() )
            return new ResponseEntity<>( libraryOptional.get(), HttpStatus.OK );

        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }

    public ResponseEntity<Library> add( Library library )
    {
        if( library.getName() == null || library.getName().length() < LIBRARY_NAME_MIN_LENGTH )
            return new ResponseEntity<>( HttpStatus.CONFLICT );

        if( libraryRepository.findByName( library.getName() ).isPresent() )
            return new ResponseEntity<>( HttpStatus.CONFLICT );

        return new ResponseEntity<>( libraryRepository.save( library ), HttpStatus.OK );
    }
}
