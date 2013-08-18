package nl.stil4m.movielibrary.provider;

public interface MovieProvider {

    SearchResult search(String movieName, Integer year, ResultFilter resultFilter, ScoreCalculator scoreCalculator);

}
