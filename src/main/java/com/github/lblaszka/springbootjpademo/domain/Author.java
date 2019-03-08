package com.github.lblaszka.springbootjpademo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table( name="AUTHOR")
public class Author
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "ID" )
    private Long id;

    @Column( name = "NAME", nullable = false )
    private String name;

    @Column( name = "SURNAME", nullable = false )
    private String surname;



}
