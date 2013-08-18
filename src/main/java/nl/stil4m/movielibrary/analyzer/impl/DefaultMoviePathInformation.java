package nl.stil4m.movielibrary.analyzer.impl;

import nl.stil4m.movielibrary.analyzer.MoviePathInformation;

public class DefaultMoviePathInformation implements MoviePathInformation {

    private final String path;
    private final Integer cdNumber;
    private final String movieName;
    private final Integer year;

    public DefaultMoviePathInformation(String path, String movieName, Integer year, Integer cdNumber) {
        this.path = path;
        this.cdNumber = cdNumber;
        this.movieName = movieName;
        this.year = year;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getMovieName() {
        return movieName;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public Integer getCdNumber() {
        return cdNumber;
    }
}
