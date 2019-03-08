package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;

public interface AuthorValidator
{
    boolean checkToAdd( Author author );
    boolean checkToUpdate( Author author );
}
