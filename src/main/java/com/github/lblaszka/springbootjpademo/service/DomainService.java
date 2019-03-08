package com.github.lblaszka.springbootjpademo.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DomainService< T, ID >
{
    ResponseEntity<T> add( T t );

    ResponseEntity<T> update( T t );

    ResponseEntity delete(T t );
    ResponseEntity deleteById(ID id );

    List<T> getAll();
    ResponseEntity<T> getById( ID id );
}
