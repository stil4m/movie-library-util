package nl.stil4m.movielibrary.library;

import java.util.Collection;

public interface AnalyzeResult {

    /**
     * Method to get the collection of analyzed movie information.
     * @return The collection of movie information.
     */
    Collection<MovieInformation> getValues();
}
