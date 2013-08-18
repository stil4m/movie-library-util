package nl.stil4m.movielibrary.provider;

import java.util.List;

public interface SearchResult {

    /**
     * Method to get all movie details affiliated to the search result.
     * @return The list of movie details.
     */
    List<MovieDetails> getMovieDetails();

    /**
     * Method to get the single result. Will only return a value if `isSingleResult` returns true.
     * @return Returns the single movie detail or null if there were more results.
     */
    MovieDetails getMovieDetail();

    /**
     * Method to check if there was only one movie found.
     * @return Returns true if only one movie was found.
     */
    Boolean isSingleResult();

}
