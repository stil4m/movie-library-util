package nl.stil4m.movielibrary.analyzer;

public interface MoviePathInformation {

    /**
     * Getter for the path that the information references to.
     * @return The path.
     */
    String getPath();

    /**
     * Getter for the movie name that was extracted from the path.
     * @return The movie name.
     */
    String getMovieName();

    /**
     * Getter for the year that was extracted from the path if it was possible to extract this number.
     * @return The year.
     */
    Integer getYear();

    /**
     * Getter for the cd number that was extracted from the path if it was possible to extract this number.
     * @return The cd number.
     */
    Integer getCdNumber();

}
