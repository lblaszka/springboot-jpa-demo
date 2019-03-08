package com.github.lblaszka.springbootjpademo.service;

public interface DomainValidator<T>
{
    boolean toAdd( T t );
    boolean toUpdate( T t );
}
