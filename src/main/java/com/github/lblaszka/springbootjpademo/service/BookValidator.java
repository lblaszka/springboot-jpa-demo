package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;

public interface BookValidator
{
    boolean check( Book book );
    boolean checkUpdate( Book book);
}
