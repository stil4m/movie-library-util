package nl.stil4m.movielibrary.analyzer.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultYearAnalyzer implements YearAnalyzer{

    private static final String YEAR_REGEX = "(\\([0-9]{4}\\)|\\{[0-9]{4}\\}|\\[[0-9]{4}\\])";
    private static final Pattern YEAR_PATTERN = Pattern.compile(YEAR_REGEX, Pattern.CASE_INSENSITIVE);
    private static final String ALTERNATIVE_YEAR_REGEX = "([0-9]{4})";
    private static final Pattern ALTERNATIVE_YEAR_PATTERN = Pattern.compile(ALTERNATIVE_YEAR_REGEX, Pattern.CASE_INSENSITIVE);

    @Override
    public Integer parseYear(String path) {
        Integer year = parseDefaultYearPattern(path);
        if (year == null) {
            year = parseAlternativeYearPattern(path);
        }
        return year;
    }

    private Integer parseDefaultYearPattern(String path) {
        Matcher yearMatcher = YEAR_PATTERN.matcher(path);
        if (yearMatcher.find()) {
            String group = yearMatcher.group(1);
            return Integer.parseInt(group.substring(1, group.length()-1));
        }
        return null;
    }

    private Integer parseAlternativeYearPattern(String path) {
        Matcher alternativeYearMatcher = ALTERNATIVE_YEAR_PATTERN.matcher(path);
        if (alternativeYearMatcher.find()) {
            return Integer.parseInt(alternativeYearMatcher.group(1));
        }
        return null;
    }

}
