package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;
import org.springframework.stereotype.Service;

@Service
public class LibraryService extends TemplateServiceImpl<Library, Long>
{
    @Override
    Long getId( Library library )
    {
        return library.getId();
    }
}
