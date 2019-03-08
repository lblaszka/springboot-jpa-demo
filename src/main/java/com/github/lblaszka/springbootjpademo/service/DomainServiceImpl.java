package com.github.lblaszka.springbootjpademo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class DomainServiceImpl< T, ID > implements DomainService<T, ID>
{
    @Autowired
    private JpaRepository<T, ID> repository;
    @Autowired
    private DomainValidator<T> validator;

    abstract ID getId( T t );

    @Override
    public ResponseEntity<T> add( T t )
    {
        if( validator.toAdd( t ) )
        {
            try
            {
                return new ResponseEntity<>( repository.save( t ), HttpStatus.OK );
            }
            catch ( Exception ex )
            {
                System.err.println( ex.getMessage() );
            }
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    @Override
    public ResponseEntity<T> update( T t )
    {
        if( validator.toUpdate( t ) && ( repository.findById( getId( t ) ).isPresent() ) )
        {
            try
            {
                return new ResponseEntity<>( repository.save( t ), HttpStatus.OK );
            }
            catch ( Exception ex )
            {
                System.err.println( ex.getMessage() );
            }
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    @Override
    public ResponseEntity delete( T t )
    {
        try
        {
            repository.delete( t );
            return new ResponseEntity<>( HttpStatus.OK );
        }
        catch ( Exception ex )
        {
            System.err.println( ex.getMessage() );
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    @Override
    public ResponseEntity deleteById( ID id )
    {
        try
        {
            repository.deleteById( id );
            return new ResponseEntity<>( HttpStatus.OK );
        }
        catch ( Exception ex )
        {
            System.err.println( ex.getMessage() );
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    @Override
    public List<T> getAll()
    {
        return repository.findAll();
    }


    @Override
    public ResponseEntity<T> getById( ID id )
    {
        Optional<T> tOptional = repository.findById( id );

        if( tOptional.isPresent() )
        {
            return new ResponseEntity<>( tOptional.get(), HttpStatus.OK );
        }
        return new ResponseEntity<>( HttpStatus.CONFLICT );
    }
}
