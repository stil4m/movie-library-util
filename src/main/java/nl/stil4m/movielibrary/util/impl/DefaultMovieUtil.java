package nl.stil4m.movielibrary.util.impl;

import nl.stil4m.movielibrary.util.FileUtil;
import nl.stil4m.movielibrary.util.MovieUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DefaultMovieUtil implements MovieUtil {

    private final List<String> extensions = Arrays.asList(".avi", ".mkv", ".mp4", ".m4a");

    private final FileUtil fileUtil;

    public DefaultMovieUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean isMovieFile(File file) {
        return extensions.contains(fileUtil.extension(file));
    }
}
