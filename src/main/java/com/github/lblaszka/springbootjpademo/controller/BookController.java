package com.github.lblaszka.springbootjpademo.controller;

import com.github.lblaszka.springbootjpademo.domain.Book;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController extends TemplateController<Book, Long>
{
}
