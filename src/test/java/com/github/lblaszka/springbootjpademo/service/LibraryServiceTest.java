package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Book;
import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.lblaszka.springbootjpademo.config.LibraryConfig.LIBRARY_NAME_MIN_LENGTH;

@RunWith( SpringRunner.class)
@SpringBootTest
public class LibraryServiceTest
{
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookService bookService;

    @Autowired
    LibraryService libraryService;

    @Test
    public void getAllTest()
    {
        libraryRepository.deleteAll();
        List<Library> libraryList = genLibraryList();

        libraryList = libraryRepository.saveAll( libraryList );
        List<Library> libraryListFromService = libraryService.getAll();

        for( int libraryIndex = 0; libraryIndex < libraryList.size(); libraryIndex++ )
        {
            Assert.assertEquals( libraryList.get( libraryIndex ).getId(), libraryListFromService.get( libraryIndex ).getId() );
            Assert.assertEquals( libraryList.get( libraryIndex ).getName(), libraryListFromService.get( libraryIndex ).getName() );
        }
    }

    @Test
    public void getByIdTest()
    {
        libraryRepository.deleteAll();
        libraryRepository.saveAll( genLibraryList() );
        int libraryNumber = (int) libraryRepository.count();
        int firstId = Math.toIntExact( libraryRepository.findAll().get( 0 ).getId() );

        for( int libraryId = firstId ; libraryId <= libraryNumber; libraryId ++ )
        {
            Assert.assertEquals(  HttpStatus.OK, libraryService.getById( new Long( libraryId ) ).getStatusCode() );
            Assert.assertEquals( libraryRepository.findById( new Long( libraryId ) ).get().getId(), libraryService.getById( new Long( libraryId ) ).getBody().getId() );
        }
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.getById( new Long( -1 ) ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.getById( new Long( 0L ) ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.getById( new Long( firstId+libraryNumber+1 ) ).getStatusCode() );

    }

    @Test
    public void addIncorrectTest()
    {
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.add( new Library(  ) ).getStatusCode());
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.add(  new Library( 0L, getIncorrectLibraryName(), null ) ).getStatusCode() );
        }

    @Test
    public void addDuplicateNameTest()
    {
        libraryRepository.deleteAll();

        Assert.assertEquals( HttpStatus.OK, libraryService.add( new Library( 0L, getCorrectLibraryNameWithIndex( 0 ), null ) ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.add( new Library( 0L, getCorrectLibraryNameWithIndex( 0 ),null ) ).getStatusCode() );
    }

    @Test
    public void addCorrectTest()
    {
        libraryRepository.deleteAll();
        Library library1 = new Library( 0L, getCorrectLibraryNameWithIndex( 0 ),null );
        Library library2 = new Library( 0L,getCorrectLibraryNameWithIndex( 1 ),null );

        Assert.assertEquals(  HttpStatus.OK, libraryService.add( library1 ).getStatusCode() );
        Assert.assertNotNull( libraryService.add( library2 ).getBody() );
    }

    @Test
    public void deleteTest()
    {
        libraryRepository.deleteAll();

        Library library1 = libraryService.add( new Library( 0L, "From deleteTest 1", null ) ).getBody();
        Library library2 = libraryService.add( new Library( 0L, "From deleteTest 2", null ) ).getBody();


        Assert.assertEquals( HttpStatus.OK, libraryService.getById( library1.getId() ).getStatusCode() );
        Assert.assertEquals( HttpStatus.OK, libraryService.delete( library1 ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.getById( library1.getId() ).getStatusCode() );

        Assert.assertEquals( HttpStatus.OK, libraryService.getById( library2.getId() ).getStatusCode() );
        Assert.assertEquals( HttpStatus.OK, libraryService.deleteById( library2.getId() ).getStatusCode() );
        Assert.assertEquals( HttpStatus.CONFLICT, libraryService.getById( library2.getId() ).getStatusCode() );

    }


    public List<Library> genLibraryList()
    {
        int itemsNumber = 5;

        List<Library> libraryList = new ArrayList<>( itemsNumber-1 );

        for( int libraryIndex = 0; libraryIndex < itemsNumber; libraryIndex++ )
        {
            libraryList.add( new Library( 0L, getCorrectLibraryNameWithIndex( libraryIndex ), new ArrayList<>(  ) ) );
        }

        return libraryList;
    }

    public String getCorrectLibraryNameWithIndex( int libraryIndex )
    {
        String str = new String();
        while( str.length() < LIBRARY_NAME_MIN_LENGTH )
            str+="L";
        return str+libraryIndex;
    }

    public String getIncorrectLibraryName()
    {
        String str = new String();
        while( str.length() < LIBRARY_NAME_MIN_LENGTH-1 )
            str+="L";
        return str;
    }


}
