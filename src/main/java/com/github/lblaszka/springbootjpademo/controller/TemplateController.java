package com.github.lblaszka.springbootjpademo.controller;

import com.github.lblaszka.springbootjpademo.service.TemplateService;
import com.github.lblaszka.springbootjpademo.service.TemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class TemplateController<T, ID>
{
    @Autowired
    TemplateServiceImpl<T, ID> templateService;

    @GetMapping
    public List<T> getAll()
    {
        return templateService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<T> getById( @PathVariable ID id )
    {
        return templateService.getById( id );
    }

    @PostMapping
    public ResponseEntity<T> add( @RequestBody T t )
    {
        return templateService.add( t );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<T> delete( @PathVariable ID id )
    {
        return templateService.deleteById( id );
    }

    @PutMapping
    public ResponseEntity<T> update( @RequestBody T t )
    {
        return templateService.update( t );
    }


}
