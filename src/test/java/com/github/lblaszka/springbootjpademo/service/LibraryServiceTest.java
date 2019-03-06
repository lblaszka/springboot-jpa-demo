package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.Library;
import com.github.lblaszka.springbootjpademo.repository.LibraryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    LibraryService libraryService;

    @Test
    public void getAllTest()
    {
        libraryRepository.deleteAllInBatch();
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
        libraryRepository.deleteAllInBatch();
        libraryRepository.saveAll( genLibraryList() );
        int libraryNumber = (int) libraryRepository.count();
        int firstId = Math.toIntExact( libraryRepository.findAll().get( 0 ).getId() );

        for( int libraryId = firstId ; libraryId <= libraryNumber; libraryId ++ )
        {
            Library library = libraryService.getById( new Long( libraryId ) ).getBody();
            Assert.assertEquals( libraryService.getById( new Long( libraryId ) ).getStatusCode(), HttpStatus.OK );
            Assert.assertEquals( libraryService.getById( new Long( libraryId ) ).getBody().getId(), libraryRepository.findById( new Long( libraryId ) ).get().getId() );
        }
        Assert.assertEquals( libraryService.getById( new Long( -1 ) ).getStatusCode(), HttpStatus.CONFLICT );
        Assert.assertEquals( libraryService.getById( new Long( 0L ) ).getStatusCode(), HttpStatus.CONFLICT );
        Assert.assertEquals( libraryService.getById( new Long( firstId+libraryNumber+1 ) ).getStatusCode(), HttpStatus.CONFLICT );

    }

    @Test
    public void addIncorrectTest()
    {
        Assert.assertEquals( libraryService.add( new Library(  ) ).getStatusCode(), HttpStatus.CONFLICT );
        Assert.assertEquals( libraryService.add( new Library( 0L, getIncorrectLibraryName(), null ) ).getStatusCode(), HttpStatus.CONFLICT );
        }

    @Test
    public void addDuplicateNameTest()
    {
        libraryRepository.deleteAllInBatch();

        Assert.assertEquals( libraryService.add( new Library( 0L, getCorrectLibraryNameWithIndex( 0 ), null ) ).getStatusCode(), HttpStatus.OK );
        Assert.assertEquals( libraryService.add( new Library( 0L, getCorrectLibraryNameWithIndex( 0 ),null ) ).getStatusCode(), HttpStatus.CONFLICT );
    }

    @Test
    public void addCorrectTest()
    {
        libraryRepository.deleteAllInBatch();
        Library library1 = new Library( 0L, getCorrectLibraryNameWithIndex( 0 ),null );
        Library library2 = new Library( 0L,getCorrectLibraryNameWithIndex( 1 ),null );

        Assert.assertEquals( libraryService.add( library1 ).getStatusCode(), HttpStatus.OK );
        Assert.assertNotNull( libraryService.add( library2 ).getBody() );
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
