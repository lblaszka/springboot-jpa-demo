package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Author;
import org.springframework.stereotype.Component;

import static com.github.lblaszka.springbootjpademo.config.AuthorConfig.AUTHOR_NAME_MIN_LENGTH;
import static com.github.lblaszka.springbootjpademo.config.AuthorConfig.AUTHOR_SURNAME_MIN_LENGTH;

@Component
public class AuthorValidator implements TemplateValidator<Author>
{
    @Override
    public boolean toAdd( Author author )
    {
        if( author == null )
            return false;

        if( author.getName() == null || author.getName().length() < AUTHOR_NAME_MIN_LENGTH )
            return false;

        if( author.getSurname() == null || author.getSurname().length() < AUTHOR_SURNAME_MIN_LENGTH )
            return false;

        if( author.getId() != null && author.getId() != 0L )
            return false;

        return true;
    }


    @Override
    public boolean toUpdate( Author author )
    {
        if( author == null )
            return false;

        if( author.getName() == null || author.getName().length() < AUTHOR_NAME_MIN_LENGTH )
            return false;

        if( author.getSurname() == null || author.getSurname().length() < AUTHOR_SURNAME_MIN_LENGTH )
            return false;

        if( author.getId() == null || author.getId() == 0L )
            return false;

        return true;
    }
}
