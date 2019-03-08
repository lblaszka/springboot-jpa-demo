package com.github.lblaszka.springbootjpademo.controller;

import com.github.lblaszka.springbootjpademo.domain.Author;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController extends TemplateController<Author, Long>
{
}
