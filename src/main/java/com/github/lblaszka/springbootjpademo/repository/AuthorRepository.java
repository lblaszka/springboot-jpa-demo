package com.github.lblaszka.springbootjpademo.repository;

import com.github.lblaszka.springbootjpademo.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>
{
}
