package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Author;
import com.github.lblaszka.springbootjpademo.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith( SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest
{

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    private List<Author> authorList;

    @Before
    public void before()
    {
        authorList = null;
        authorRepository.deleteAll();
        authorList = new ArrayList<>(  );

        authorList.add( new Author( null, "CorrectName", "CorrectSurname" ) );
        authorList.add( new Author( null, "CorrectName", "CorrectSurname" ) );
        authorList.add( new Author( null, "CorrectName", "CorrectSurname" ) );

        authorList = authorRepository.saveAll( authorList );
    }

    @Test
    public void getAll()
    {
        Assert.assertEquals( authorList, authorService.getAll() );
    }

    @Test
    public void getById()
    {
        for( Author author : authorList )
        {
            ResponseEntity<Author> authorResponseEntity = authorService.getById( author.getId() );
            Assert.assertEquals( HttpStatus.OK, authorResponseEntity.getStatusCode() );
            Assert.assertEquals( author, authorResponseEntity.getBody() );
        }
    }

    @Test
    public void getByIdOfNotExistAuthor()
    {
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.getById( 0L ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.getById( -10L ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.getById( authorList.get( 2 ).getId() + 1 ).getStatusCode() );
    }

    @Test
    public void addCorrect()
    {
        Author authorNew = new Author( 0L, "CorrectName", "CorrectSurname" );
        ResponseEntity<Author> authorResponseEntity = authorService.add( authorNew );

        //Compare datas with returned author object
        Assert.assertEquals( HttpStatus.OK, authorResponseEntity.getStatusCode() );
        Assert.assertNotNull( authorResponseEntity.getBody() );
        Assert.assertNotEquals( new Long(0), authorResponseEntity.getBody().getId() );
        Assert.assertEquals( authorNew.getName(), authorResponseEntity.getBody().getName() );
        Assert.assertEquals( authorNew.getSurname(), authorResponseEntity.getBody().getSurname() );

        //test atas with author from db
        Assert.assertEquals( authorResponseEntity.getBody(), authorService.getById( authorResponseEntity.getBody().getId() ).getBody() );
    }

    @Test
    public void addIncorrect()
    {
        authorRepository.deleteAll();

        Author authorEmpty = new Author( );
        Author authorBadValues = new Author( 1L, "", "" );
        Author authorBadId = new Author( 1L, "CorrectName", "CorrectSurname" );
        Author authorBadName = new Author( 0L, "", "CorrectSurname" );
        Author authorBadSurname = new Author( 0L, "CorrectName", "" );

        Assert.assertEquals( HttpStatus.CONFLICT, authorService.add( authorEmpty ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.add( authorBadValues ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.add( authorBadId ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.add( authorBadName ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorService.add( authorBadSurname ).getStatusCode() );
        Assert.assertEquals( 0, authorRepository.count() );
    }

    @Test
    public void updateCorrect()
    {
        ResponseEntity<Author> authorResponseEntityWithChangedName;
        ResponseEntity<Author> authorResponseEntityWithChangedSurname;
        ResponseEntity<Author> authorResponseEntityWithChangedValues;

        authorList.get( 0 ).setName( "NewCorrectName" );
        authorList.get( 1 ).setSurname( "newCorrectSurname" );
        authorList.get( 2 ).setName( "newCorrectName" );
        authorList.get( 2 ).setSurname( "newCorrectSurname" );

        //Update
        authorResponseEntityWithChangedName = authorService.update( authorList.get( 0 ) );
        authorResponseEntityWithChangedSurname = authorService.update( authorList.get( 1 ) );
        authorResponseEntityWithChangedValues = authorService.update( authorList.get( 2 ) );

        //Check success update
        Assert.assertEquals( HttpStatus.OK, authorResponseEntityWithChangedName.getStatusCode() );
        Assert.assertEquals( HttpStatus.OK, authorResponseEntityWithChangedSurname.getStatusCode() );
        Assert.assertEquals( HttpStatus.OK, authorResponseEntityWithChangedValues.getStatusCode() );

        //Check correct return author object
        Assert.assertEquals( authorList.get( 0 ), authorResponseEntityWithChangedName.getBody() );
        Assert.assertEquals( authorList.get( 1 ), authorResponseEntityWithChangedSurname.getBody() );
        Assert.assertEquals( authorList.get( 2 ), authorResponseEntityWithChangedValues.getBody() );

        //Check correct save changes in db
        Assert.assertEquals( authorResponseEntityWithChangedName.getBody(), authorService.getById( authorList.get( 0 ).getId() ).getBody() );
        Assert.assertEquals( authorResponseEntityWithChangedSurname.getBody(), authorService.getById( authorList.get( 1 ).getId() ).getBody() );
        Assert.assertEquals( authorResponseEntityWithChangedValues.getBody(), authorService.getById( authorList.get( 2 ).getId() ).getBody() );

        Assert.assertEquals( 3, authorRepository.count() );
    }

    @Test
    public void updateIncorrect()
    {
        Author authorZeroId = new Author( 0L, "CorrectName", "CorrectSurname" );
        Long authorIdNotExist = authorRepository.count() + 1;
        Author authorNotExist = new Author( authorIdNotExist, "CorrectName", "CorrectSurname" );

        ResponseEntity<Author> authorResponseEntityWithBadChangedName;
        ResponseEntity<Author> authorResponseEntityWithBadChangedSurname;
        ResponseEntity<Author> authorResponseEntityWithBadChangedValues;
        ResponseEntity<Author> authorResponseEntityWithZeroId;
        ResponseEntity<Author> authorResponseEntityNotExisting;

        authorList.get( 0 ).setName( "" );
        authorList.get( 1 ).setSurname( "" );
        authorList.get( 2 ).setName( "" );
        authorList.get( 2 ).setSurname( "" );

        //Update
        authorResponseEntityWithBadChangedName = authorService.update( authorList.get( 0 ) );
        authorResponseEntityWithBadChangedSurname = authorService.update( authorList.get( 1 ) );
        authorResponseEntityWithBadChangedValues = authorService.update( authorList.get( 2 ) );
        authorResponseEntityWithZeroId = authorService.update( authorZeroId );
        authorResponseEntityNotExisting = authorService.update( authorNotExist );

        //Check unsuccessful update
        Assert.assertEquals( HttpStatus.CONFLICT, authorResponseEntityWithBadChangedName.getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorResponseEntityWithBadChangedSurname.getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorResponseEntityWithBadChangedValues.getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorResponseEntityWithZeroId.getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, authorResponseEntityNotExisting.getStatusCode() );

        //Check return null
        Assert.assertNull( authorResponseEntityWithBadChangedName.getBody() );
        Assert.assertNull( authorResponseEntityWithBadChangedSurname.getBody() );
        Assert.assertNull( authorResponseEntityWithBadChangedValues.getBody() );
        Assert.assertNull( authorResponseEntityWithZeroId.getBody() );
        Assert.assertNull( authorResponseEntityNotExisting.getBody() );

        //Check not changes dates on db
        Assert.assertNotEquals( authorResponseEntityWithBadChangedName.getBody(), authorService.getById( authorList.get( 0 ).getId() ) );
        Assert.assertNotEquals( authorResponseEntityWithBadChangedSurname.getBody(), authorService.getById( authorList.get( 1 ).getId() ) );
        Assert.assertNotEquals( authorResponseEntityWithBadChangedValues.getBody(), authorService.getById( authorList.get( 2 ).getId() ) );

        Assert.assertEquals( 3, authorRepository.count() );
    }


}
