package com.github.lblaszka.springbootjpademo.repository;

import com.github.lblaszka.springbootjpademo.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long>
{
    Optional<Library> findByName( String name );
}
