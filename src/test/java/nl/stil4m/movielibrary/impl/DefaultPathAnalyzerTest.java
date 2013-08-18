package nl.stil4m.movielibrary.impl;

import nl.stil4m.movielibrary.analyzer.MoviePathAnalyzer;
import nl.stil4m.movielibrary.analyzer.MoviePathInformation;
import nl.stil4m.movielibrary.analyzer.impl.DefaultMoviePathAnalyzer;

import nl.stil4m.movielibrary.analyzer.impl.DefaultYearAnalyzer;
import nl.stil4m.movielibrary.analyzer.impl.YearAnalyzer;
import nl.stil4m.movielibrary.util.FileUtil;
import nl.stil4m.movielibrary.util.impl.DefaultFileUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DefaultPathAnalyzerTest {

    private MoviePathAnalyzer moviePathAnalyzer;

    private FileUtil fileUtil = new DefaultFileUtil();

    private YearAnalyzer yearAnalyzer = new DefaultYearAnalyzer();
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        moviePathAnalyzer = new DefaultMoviePathAnalyzer(fileUtil, yearAnalyzer);
    }

    @Test
    public void testAnalyze() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("42 (2013)" + File.separator + "42.mp4");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(nullValue()));
        assertThat(moviePathInformation.getYear(), is(2013));
        assertThat(moviePathInformation.getMovieName(), is("42"));
    }
    
    @Test
    public void testAnalyze2() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("42 (2013).mp4");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(nullValue()));
        assertThat(moviePathInformation.getYear(), is(2013));
        assertThat(moviePathInformation.getMovieName(), is("42"));
    }

    @Test
    public void testAnalyze3() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("42.mp4");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(nullValue()));
        assertThat(moviePathInformation.getYear(), is(nullValue()));
        assertThat(moviePathInformation.getMovieName(), is("42"));
    }

    @Test
    public void testAnalyze4() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("Wall Street Money Never Sleeps (2010)" + File.separator+ "Wall Street Money Never Sleeps cd1.avi");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getYear(), is(2010));
        assertThat(moviePathInformation.getMovieName(), is("Wall Street Money Never Sleeps"));
        assertThat(moviePathInformation.getCdNumber(), is(1));
    }

    @Test
    public void testAnalyze5() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("Wall Street Money Never Sleeps (2010)" + File.separator+ "Wall Street Money Never Sleeps cd2.avi");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(2));
        assertThat(moviePathInformation.getYear(), is(2010));
        assertThat(moviePathInformation.getMovieName(), is("Wall Street Money Never Sleeps"));
    }

    @Test
    public void testAnalyze6() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("Wall Street Money Never Sleeps (2010)" + File.separator+ "Wall Street Money Never Sleeps CD3.avi");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(3));
        assertThat(moviePathInformation.getYear(), is(2010));
        assertThat(moviePathInformation.getMovieName(), is("Wall Street Money Never Sleeps"));
    }

    @Test
    public void testAnalyze7() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("2012 (2009)" + File.separator+ "2012.avi");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(nullValue()));
        assertThat(moviePathInformation.getYear(), is(2009));
        assertThat(moviePathInformation.getMovieName(), is("2012"));
    }

    @Test
    public void testAnalyze8() {
        MoviePathInformation moviePathInformation = moviePathAnalyzer.analyze("2012.avi");
        assertThat(moviePathInformation, is(notNullValue()));
        assertThat(moviePathInformation.getCdNumber(), is(nullValue()));
        assertThat(moviePathInformation.getYear(), is(nullValue()));
        assertThat(moviePathInformation.getMovieName(), is("2012"));
    }
}
