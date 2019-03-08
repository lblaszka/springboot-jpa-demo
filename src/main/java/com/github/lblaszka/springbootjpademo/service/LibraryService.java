package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.lblaszka.springbootjpademo.config.LibraryConfig.LIBRARY_NAME_MIN_LENGTH;

@Service
public class LibraryService extends DomainServiceImpl<Library, Long>
{
    @Override
    Long getId( Library library )
    {
        return library.getId();
    }
}
