package com.github.lblaszka.springbootjpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table( name = "LIBRARY" )
public class Library
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "ID" )
    private Long id;

    @Column( name = "NAME", nullable = false, unique = true )
    private String name;

    @OneToMany( mappedBy = "library", orphanRemoval = true )
    @JsonIgnore
    private List<Book> bookList;
}
