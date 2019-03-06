package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class)
@SpringBootTest
public class BookServiceTest
{
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void getAll()
    {

    }
}
