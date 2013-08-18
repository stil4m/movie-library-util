package nl.stil4m.movielibrary.library;

import nl.stil4m.movielibrary.analyzer.MoviePathInformation;
import nl.stil4m.movielibrary.provider.MovieDetails;

import java.util.List;

public interface MovieInformation {

    MovieDetails getMovieDetails();

    List<MoviePathInformation> getMoviePathInformationList();
}
