package nl.stil4m.movielibrary.library.impl;

import nl.stil4m.movielibrary.library.AnalyzeResult;
import nl.stil4m.movielibrary.library.MovieInformation;

import java.util.Collection;

public class DefaultAnalyzeResult implements AnalyzeResult {

    private final Collection<MovieInformation> values;

    public DefaultAnalyzeResult(Collection<MovieInformation> values) {
        this.values = values;
    }

    @Override
    public Collection<MovieInformation> getValues() {
        return values;
    }
}
