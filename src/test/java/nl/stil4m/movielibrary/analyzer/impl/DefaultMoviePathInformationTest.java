package nl.stil4m.movielibrary.analyzer.impl;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultMoviePathInformationTest {

    @Test
    public void testConstructor() {
        DefaultMoviePathInformation defaultMoviePathInformation = new DefaultMoviePathInformation("path", "name", 1997, 5);
        assertThat(defaultMoviePathInformation.getYear(), is(1997));
        assertThat(defaultMoviePathInformation.getCdNumber(), is(5));
        assertThat(defaultMoviePathInformation.getMovieName(), is("name"));
        assertThat(defaultMoviePathInformation.getPath(), is("path"));
    }
}
