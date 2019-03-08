package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import org.springframework.stereotype.Component;

import static com.github.lblaszka.springbootjpademo.config.BookConfig.BOOK_NAME_NIM_LENGTH;

@Component
public class BookValidatorImpl implements DomainValidator<Book>
{

    @Override
    public boolean toAdd( Book book )
    {
        if( book == null )
            return false;
        if( book.getName() == null || book.getName().length() < BOOK_NAME_NIM_LENGTH )
            return false;
        if( book.getLibrary() == null )
            return false;

        return true;
    }


    @Override
    public boolean toUpdate( Book book )
    {
        if( this.toAdd( book ) )
        {
            if( book.getId() > 0L )
                return true;
        }
        return false;
    }
}
