package nl.stil4m.movielibrary.util.impl;

import nl.stil4m.movielibrary.util.FileUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class DefaultFileUtilTest {

    private FileUtil fileUtil;

    @Mock
    private File file;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        fileUtil = new DefaultFileUtil();
    }

    @Test
    public void testFileWithExtension() {
        when(file.getName()).thenReturn("fileName.txt");
        assertThat(fileUtil.extension(file), is(".txt"));
    }

    @Test
    public void testFileWithoutExtension() {
        when(file.getName()).thenReturn("fileName");
        assertThat(fileUtil.extension(file), is(nullValue()));
    }
}
