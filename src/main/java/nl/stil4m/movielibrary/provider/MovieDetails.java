package nl.stil4m.movielibrary.provider;

public interface MovieDetails {

    /**
     * Score from 0.0 up to 1.0 indicating the relevance for the MovieDetail
     * @return The relevance
     */
    Double getScore();

    /**
     * The id for the source that provided the information
     * @return The source id.
     */
    String getSourceId();

    /**
     * The id that is used and can be used by the source to reference the movie.
     * @return The reference id.
     */
    String getSourceReferenceId();

    /**
     * The movie name as provided by the source.
     * @return The movie name.
     */
    String getMovieName();

    /**
     * The year of the movie as provided by the source.
     * @return The year.
     */
    Integer getYear();
}
