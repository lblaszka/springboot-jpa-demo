package com.github.lblaszka.springbootjpademo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "BOOK" )
public class Book
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "ID" )
    private Long id;

    @Column( name = "NAME", nullable = false )
    private String name;

    @ManyToOne
    @JoinColumn( name = "LIBRARY_ID", nullable = false )
    private Library library;


    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Book book = (Book) o;
        return Objects.equals( id, book.id ) &&
                Objects.equals( name, book.name );
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( id, name );
    }
}
