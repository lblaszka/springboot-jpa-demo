package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.BookRepository;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class)
@SpringBootTest
public class BookServiceTest
{
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Test
    public void getAll()
    {

    }

    @Test
    public void addWithNotExistLibrary()
    {
        libraryRepository.deleteAll();

        Book book = new Book( 0L, "Jakas nazwa", new Library( 1L, "Not exist", null ) );

        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( book ).getStatusCode() );
    }

    @Test
    public void addWithoutLibrary()
    {
        Book book = new Book( 0L, "Jakas nazwa", null );

        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( book ).getStatusCode() );
    }

    @Test
    public void addCorrect()
    {
        Library library = libraryRepository.save( new Library( 0L, "From addWithLibraryTest", null ) );

        Assert.assertEquals( HttpStatus.OK, bookService.add( new Book( 0L, "Book", library) ).getStatusCode() );
    }

    @Test
    public void addWithIncorrectName()
    {
        Library library = libraryRepository.save( new Library( 0L, "From addWithLibraryTest", null ) );

        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( 0L, null, library) ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( 0L, "", library) ).getStatusCode() );
    }

    @Test
    public void deleteLibraryWithBooks()
    {
        Library library = libraryRepository.save( new Library( 0L, "From deleteLibraryWithBooksTest", null ) );

        Assert.assertEquals( HttpStatus.OK, bookService.add( new Book( 0L, "Book", library) ).getStatusCode() );
        Book book = bookService.add( new Book( 0L, "Book", library) ).getBody();
        Assert.assertEquals( HttpStatus.OK, bookService.getById( book.getId() ).getStatusCode() );

        libraryRepository.deleteById( library.getId() );
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.getById( book.getId() ).getStatusCode() );
    }

    @Test
    public void deleteTest()
    {
        Library library = libraryRepository.save( new Library( 0L, "From deleteLibraryWithBooksTest", null ) );

        Book book1 = bookService.add( new Book( 0L, "Correct Name", library) ).getBody();
        Book book2 = bookService.add( new Book( 0L, "Correct Name", library) ).getBody();

        Assert.assertEquals( HttpStatus.OK, bookService.getById( book1.getId() ).getStatusCode() );
        Assert.assertEquals( HttpStatus.OK, bookService.delete( book1 ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.getById( book1.getId() ).getStatusCode() );

        Assert.assertEquals( HttpStatus.OK, bookService.getById( book2.getId() ).getStatusCode() );
        Assert.assertEquals( HttpStatus.OK, bookService.deleteById( book2.getId() ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.getById( book2.getId() ).getStatusCode() );

    }


}
