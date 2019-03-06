package com.github.lblaszka.springbootjpademo.repository;

import com.github.lblaszka.springbootjpademo.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>
{
}
