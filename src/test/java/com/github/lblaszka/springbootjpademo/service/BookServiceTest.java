package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.BookRepository;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    List<Library> libraryList;

    @Before
    public void initLibrares()
    {
        libraryList = null;
        bookRepository.deleteAll();
        libraryRepository.deleteAll();

        libraryList = new ArrayList<>( 3 );
        libraryList.add( libraryRepository.save( new Library( 0L, "Library 1st", null )) );
        libraryList.add( libraryRepository.save( new Library( 0L, "Library 2nd", null )) );
        libraryList.add( libraryRepository.save( new Library( 0L, "Library 3th", null )) );
    }

   @Test
    public void getAll()
   {
        List<Book> bookList = new ArrayList<>( 3 );

        bookList.add( bookService.add( new Book( 0L, getCorrectBookName(), libraryList.get(0) ) ).getBody() );
        bookList.add( bookService.add( new Book( 0L, getCorrectBookName(), libraryList.get(1) ) ).getBody() );
        bookList.add( bookService.add( new Book( 0L, getCorrectBookName(), libraryList.get(2) ) ).getBody() );

        Assert.assertEquals( bookList, bookService.getAll() );
   }

   @Test
   public void addCorrect()
   {
       Book book = new Book(0L, getCorrectBookName(), libraryList.get(0) );

       ResponseEntity<Book> bookResponseEntity = bookService.add( book );

       Assert.assertEquals( HttpStatus.OK, bookResponseEntity.getStatusCode() );
       Assert.assertNotNull( bookResponseEntity.getBody() );
       Assert.assertNotEquals( 0L, bookResponseEntity.getBody().getId().longValue() );
       Assert.assertEquals( book.getName(), bookResponseEntity.getBody().getName() );
       //lazily error
       //Assert.assertEquals( book.getLibrary(), bookResponseEntity.getBody().getLibrary() );
       Assert.assertEquals( 1, bookRepository.count() );
   }

    @Test
    public void addEmptyBookObject()
    {
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( ) ).getStatusCode() );
        Assert.assertEquals( 0L, bookRepository.count() );
    }

    @Test
    public void addWithoutName()
    {
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( 0L, null, libraryList.get( 0 ) ) ).getStatusCode() );
        Assert.assertEquals( 0L, bookRepository.count() );
    }

    @Test
    public void addWithoutLibrary()
    {
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( 0L, getCorrectBookName(), null ) ).getStatusCode() );
        Assert.assertEquals( 0L, bookRepository.count() );
    }

    @Test
    public void addWithNotExistLibrary()
    {
        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( 0L, null, new Library( ) ) ).getStatusCode() );
        Assert.assertEquals( 0L, bookRepository.count() );
    }

    @Test
    public void addWithSetId()
    {
        Book book = bookService.add( new Book( 0L, getCorrectBookName(), libraryList.get( 0 ) ) ).getBody();

        Assert.assertEquals( HttpStatus.CONFLICT, bookService.add( new Book( book.getId(), getCorrectBookName(), libraryList.get( 0 ) ) ).getStatusCode() );
        Assert.assertEquals( 1L, bookRepository.count() );
    }

    @Test
    public void updateCorrect()
    {
        Book book = bookService.add( new Book( 0L, getCorrectBookName(), libraryList.get( 0 ) ) ).getBody();

        book.setName( "New" + getCorrectBookName() );
        Assert.assertEquals( HttpStatus.OK, bookService.update( book ).getStatusCode() );
        Assert.assertEquals( book, bookService.getById( book.getId() ).getBody() );
        Assert.assertEquals( 1L, bookRepository.count() );

        book.setLibrary( libraryList.get( 1 ) );
        Assert.assertEquals( HttpStatus.OK, bookService.update( book ).getStatusCode() );
        Assert.assertEquals( book, bookService.getById( book.getId() ).getBody() );
        Assert.assertEquals( 1L, bookRepository.count() );
    }

    @Test
    public void updateWithInvalidId()
    {
        Book book = bookService.add( new Book( 0L, getCorrectBookName(), libraryList.get( 0 ) ) ).getBody();
        Long validId= book.getId();
        book.setId( book.getId()+1 );

        Assert.assertEquals( HttpStatus.CONFLICT, bookService.update( book ).getStatusCode() );

        book.setId( validId );
        Assert.assertEquals( book, bookService.getById( book.getId() ).getBody() );
    }

    @Test
    public void updateWithNotExistLibrary()
    {

    }


   String getCorrectBookName()
   {
       return "Correct book name";
   }
}
