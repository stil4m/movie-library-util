package nl.stil4m.movielibrary.analyzer;

public interface MoviePathAnalyzer {

    /**
     * Method to analyze a path and retrieve the available movie information for that path
     * @param path The file path that should be analyzed as a String.
     * @return The movie path information.
     */
    MoviePathInformation analyze(String path);

}
