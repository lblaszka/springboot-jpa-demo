package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends TemplateServiceImpl<Author, Long>
{
    @Override
    Long getId( Author author )
    {
        return author.getId();
    }
}
