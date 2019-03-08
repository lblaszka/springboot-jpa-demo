package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;
import org.springframework.stereotype.Component;

import static com.github.lblaszka.springbootjpademo.config.LibraryConfig.LIBRARY_NAME_MIN_LENGTH;

@Component
public class LibraryValidator implements TemplateValidator<Library>
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
