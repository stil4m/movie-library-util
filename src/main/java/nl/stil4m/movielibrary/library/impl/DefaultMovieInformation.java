package nl.stil4m.movielibrary.library.impl;

import nl.stil4m.movielibrary.analyzer.MoviePathInformation;
import nl.stil4m.movielibrary.library.MovieInformation;
import nl.stil4m.movielibrary.provider.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class DefaultMovieInformation implements MovieInformation {

    private final MovieDetails movieDetails;

    private final List<MoviePathInformation> moviePathInformationList;

    public DefaultMovieInformation(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
        moviePathInformationList = new ArrayList<MoviePathInformation>();
    }

    @Override
    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    @Override
    public List<MoviePathInformation> getMoviePathInformationList() {
        return moviePathInformationList;
    }

    public void addPathInformation(MoviePathInformation moviePathInformation) {
        moviePathInformationList.add(moviePathInformation);
    }
}
