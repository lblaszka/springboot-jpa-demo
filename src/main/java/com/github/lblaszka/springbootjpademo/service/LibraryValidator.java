package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;

import static com.github.lblaszka.springbootjpademo.config.LibraryConfig.LIBRARY_NAME_MIN_LENGTH;

public class LibraryValidatorImpl implements DomainValidator<Library>
{
    @Override
    public boolean toAdd( Library library )
    {
        if( library == null )
            return false;
        if( library.getName() == null || library.getName().length() < LIBRARY_NAME_MIN_LENGTH )
            return false;
        if( library.getId() != null && library.getId() != 0 )
            return false;

        return true;
    }


    @Override
    public boolean toUpdate( Library library )
    {
        if( library == null )
            return false;
        if( library.getName() == null || library.getName().length() < LIBRARY_NAME_MIN_LENGTH )
            return false;
        if( library.getId() == null || library.getId() == 0 )
            return false;

        return false;
    }
}
