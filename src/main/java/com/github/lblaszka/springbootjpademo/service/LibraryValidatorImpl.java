package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;

public class LibraryValidatorImpl implements LibraryValidator
{
    @Override
    public boolean check( Library library )
    {
        return false;
    }
}
