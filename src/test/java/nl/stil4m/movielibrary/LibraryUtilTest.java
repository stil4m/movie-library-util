package nl.stil4m.movielibrary;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import nl.stil4m.movielibrary.analyzer.MoviePathAnalyzer;
import nl.stil4m.movielibrary.analyzer.MoviePathInformation;
import nl.stil4m.movielibrary.analyzer.impl.DefaultMoviePathInformation;
import nl.stil4m.movielibrary.library.AnalyzeResult;
import nl.stil4m.movielibrary.library.FileManager;
import nl.stil4m.movielibrary.library.MovieInformation;
import nl.stil4m.movielibrary.library.impl.DefaultLibraryUtil;
import nl.stil4m.movielibrary.provider.*;
import nl.stil4m.movielibrary.util.MovieUtil;
import nl.stil4m.movielibrary.util.impl.DefaultFileUtil;
import nl.stil4m.movielibrary.util.impl.DefaultMovieUtil;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class LibraryUtilTest {

    private DefaultLibraryUtil defaultLibraryUtil;

    @Mock
    private MoviePathAnalyzer moviePathAnalyzer;

    @Mock
    private MovieProvider movieProvider;

    @Mock
    private FileManager fileManager;

    @Mock
    private ScoreCalculator scoreCalculator;

    @Mock
    private ResultFilter resultFilter;


    private MovieUtil movieUtil = new DefaultMovieUtil(new DefaultFileUtil());

    private File tempDir;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        tempDir = Files.createTempDir();
        try {
            File dir = createDir(tempDir, "42 (2013)");
            File fileW = createFile(tempDir, "42 (2013)/42-fanart.jpg");
            File fileX = createFile(tempDir, "42 (2013)/42.mp4");
            File fileY = createFile(tempDir, "42 (2013)/42.nfo");
            File fileZ = createFile(tempDir, "42 (2013)/42.tbn");
            when(fileManager.listFiles(dir)).thenReturn(Arrays.asList(fileW, fileX, fileY, fileZ));

            File fileA = createFile(tempDir, "42 2013.avi");
            File fileB = createFile(tempDir, "John Carter (2012).avi");

            when(fileManager.listFiles(tempDir)).thenReturn(Arrays.asList(dir, fileA, fileB));
        } catch (IOException e) {
            fail();
        }


        defaultLibraryUtil = new DefaultLibraryUtil(moviePathAnalyzer, movieProvider, fileManager, resultFilter, scoreCalculator, movieUtil);
    }

    private File createFile(File tempDir, String s) throws IOException {
        File f = new File(tempDir, s);
        f.createNewFile();
        return f;
    }

    private File createDir(File tempDir, String s) {
        File f = new File(tempDir, s);
        f.mkdirs();
        return f;
    }

    @org.junit.Test
    public void testAnalyze() {
        MoviePathInformation moviePathInformationOne = new DefaultMoviePathInformation("42 (2013)/42.mp4", "42", 2013, null);
        MoviePathInformation moviePathInformationTwo = new DefaultMoviePathInformation("42 2013.avi", "42", 2013, null);
        MoviePathInformation moviePathInformationThree = new DefaultMoviePathInformation("John Carter (2012).avi", "John Carter", 2012, null);

        when(moviePathAnalyzer.analyze("42 (2013)/42.mp4")).thenReturn(moviePathInformationOne);
        when(moviePathAnalyzer.analyze("42 2013.avi")).thenReturn(moviePathInformationTwo);
        when(moviePathAnalyzer.analyze("John Carter (2012).avi")).thenReturn(moviePathInformationThree);

        MovieDetails movieDetail42 = mock(MovieDetails.class);
        MovieDetails movieDetailJohnCarter = mock(MovieDetails.class);

        SearchResult searchResultOne = mock(SearchResult.class);
        SearchResult searchResultTwo = mock(SearchResult.class);
        when(searchResultOne.getMovieDetails()).thenReturn(Lists.newArrayList(movieDetail42));
        when(searchResultTwo.getMovieDetails()).thenReturn(Lists.newArrayList(movieDetailJohnCarter));

        when(movieProvider.search("42", 2013, resultFilter, scoreCalculator)).thenReturn(searchResultOne);
        when(movieProvider.search("John Carter", 2012, resultFilter, scoreCalculator)).thenReturn(searchResultTwo);

        AnalyzeResult analyzeResult = defaultLibraryUtil.analyze(tempDir);

        verify(movieProvider, times(1)).search("42", 2013, resultFilter, scoreCalculator);
        verify(movieProvider, times(1)).search("John Carter", 2012, resultFilter, scoreCalculator);
        verify(moviePathAnalyzer).analyze("42 (2013)/42.mp4");
        verify(moviePathAnalyzer).analyze("42 2013.avi");
        verify(moviePathAnalyzer).analyze("John Carter (2012).avi");
        verify(moviePathAnalyzer, never()).analyze("42 (2013)");
        verify(moviePathAnalyzer, never()).analyze("42 (2013)/42.tbn");

        assertThat(analyzeResult, is(notNullValue()));
        assertThat(analyzeResult.getValues(), is(notNullValue()));

        List<MovieInformation> defaultMovieInformationList = Lists.newArrayList(analyzeResult.getValues());

        assertThat(defaultMovieInformationList.size(), is(2));

        assertThat(defaultMovieInformationList.get(0).getMovieDetails(), is(movieDetail42));
        assertThat(defaultMovieInformationList.get(0).getMoviePathInformationList().size(), is(2));
        assertThat(defaultMovieInformationList.get(0).getMoviePathInformationList().get(0), is(moviePathInformationOne));
        assertThat(defaultMovieInformationList.get(0).getMoviePathInformationList().get(1), is(moviePathInformationTwo));

        assertThat(defaultMovieInformationList.get(1).getMovieDetails(), is(movieDetailJohnCarter));
        assertThat(defaultMovieInformationList.get(1).getMoviePathInformationList().size(), is(1));
        assertThat(defaultMovieInformationList.get(1).getMoviePathInformationList().get(0), is(moviePathInformationThree));
    }
}
