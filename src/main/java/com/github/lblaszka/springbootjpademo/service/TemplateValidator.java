package com.github.lblaszka.springbootjpademo.service;

public interface TemplateValidator<T>
{
    boolean toAdd( T t );
    boolean toUpdate( T t );
}
