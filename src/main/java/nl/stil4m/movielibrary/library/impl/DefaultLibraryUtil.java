package nl.stil4m.movielibrary.library.impl;

import nl.stil4m.movielibrary.analyzer.MoviePathAnalyzer;
import nl.stil4m.movielibrary.analyzer.MoviePathInformation;
import nl.stil4m.movielibrary.library.AnalyzeResult;
import nl.stil4m.movielibrary.library.FileManager;
import nl.stil4m.movielibrary.library.LibraryUtil;
import nl.stil4m.movielibrary.library.MovieInformation;
import nl.stil4m.movielibrary.provider.MovieProvider;
import nl.stil4m.movielibrary.provider.ResultFilter;
import nl.stil4m.movielibrary.provider.ScoreCalculator;
import nl.stil4m.movielibrary.provider.SearchResult;
import nl.stil4m.movielibrary.util.MovieUtil;

import java.io.File;
import java.util.*;

public class DefaultLibraryUtil implements LibraryUtil {

    private final MoviePathAnalyzer moviePathAnalyzer;
    private final MovieProvider movieProvider;
    private final ResultFilter resultFilter;
    private final ScoreCalculator scoreCalculator;
    private final MovieUtil movieUtil;
    private final FileManager fileManager;

    public DefaultLibraryUtil(MoviePathAnalyzer moviePathAnalyzer, final MovieProvider movieProvider, FileManager fileManager, ResultFilter resultFilter, ScoreCalculator scoreCalculator, MovieUtil movieUtil) {
        this.moviePathAnalyzer = moviePathAnalyzer;
        this.movieProvider = movieProvider;
        this.fileManager = fileManager;
        this.resultFilter = resultFilter;
        this.scoreCalculator = scoreCalculator;
        this.movieUtil = movieUtil;
    }

    @Override
    public AnalyzeResult analyze(final File target) {
        final List<File> allFiles = getVideoFiles(target);
        final Map<String, DefaultMovieInformation> movieInformationMap = new HashMap<String, DefaultMovieInformation>();
        final List<MoviePathInformation> moviePathInformationList = new ArrayList<MoviePathInformation>();

        for (final File file : allFiles) {
            String relativePath = file.getPath().replace(target.getPath() + File.separator, "");
            moviePathInformationList.add(moviePathAnalyzer.analyze(relativePath));
        }

        for (final MoviePathInformation moviePathInformation : moviePathInformationList) {
            handleMoviePathInformation(movieInformationMap, moviePathInformation);
        }

        Collection<MovieInformation> movieInformationCollection = new ArrayList<MovieInformation>();
        for (MovieInformation movieInformation : movieInformationMap.values()) {
            movieInformationCollection.add(movieInformation);
        }
        return new DefaultAnalyzeResult(movieInformationCollection);
    }

    private void handleMoviePathInformation(Map<String, DefaultMovieInformation> movieInformationMap, MoviePathInformation moviePathInformation) {
        String key = buildMoviePathInformationKey(moviePathInformation);

        if (!movieInformationMap.containsKey(key)) {
            SearchResult searchResult = movieProvider.search(moviePathInformation.getMovieName(), moviePathInformation.getYear(), resultFilter, scoreCalculator);
            DefaultMovieInformation defaultMovieInformation = getDefaultMovieInformationForSearchResult(searchResult);
            movieInformationMap.put(key, defaultMovieInformation);
        }
        DefaultMovieInformation defaultMovieInformation = movieInformationMap.get(key);
        defaultMovieInformation.addPathInformation(moviePathInformation);
    }

    private DefaultMovieInformation getDefaultMovieInformationForSearchResult(SearchResult searchResult) {
        if (searchResult.getMovieDetails().size() == 0) {
            return new DefaultMovieInformation(null);
        } else {
            return new DefaultMovieInformation(searchResult.getMovieDetails().get(0));
        }
    }

    private String buildMoviePathInformationKey(MoviePathInformation moviePathInformation) {
        return moviePathInformation.getMovieName() + " " + File.pathSeparator + " " + moviePathInformation.getYear();
    }

    private List<File> getVideoFiles(File target) {
        List<File> videoFiles = new ArrayList<File>();
        for (File file : fileManager.listFiles(target)) {
            if (file.isDirectory()) {
                videoFiles.addAll(getVideoFiles(file));
            } else if (movieUtil.isMovieFile(file)) {
                videoFiles.add(file);
            }
        }
        return videoFiles;
    }
}
