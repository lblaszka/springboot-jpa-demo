package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService extends TemplateServiceImpl<Book, Long>
{
    @Override
    Long getId( Book book )
    {
        return book.getId();
    }
}
