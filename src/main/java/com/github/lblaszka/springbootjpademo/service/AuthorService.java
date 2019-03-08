package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Author;
import com.github.lblaszka.springbootjpademo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService extends DomainServiceImpl<Author, Long>
{
    @Override
    Long getId( Author author )
    {
        return author.getId();
    }
}
