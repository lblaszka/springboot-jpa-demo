package com.github.lblaszka.springbootjpademo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @Column( nullable = false )
    private String name;

    @ManyToOne
    @JoinColumn( name = "LIBRARY_ID", nullable = false )
    private Library library;
}
