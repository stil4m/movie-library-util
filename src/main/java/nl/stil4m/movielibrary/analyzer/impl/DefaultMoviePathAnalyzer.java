package nl.stil4m.movielibrary.analyzer.impl;

import nl.stil4m.movielibrary.analyzer.MoviePathAnalyzer;
import nl.stil4m.movielibrary.analyzer.MoviePathInformation;
import nl.stil4m.movielibrary.util.FileUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultMoviePathAnalyzer implements MoviePathAnalyzer {

    private static final String YEAR_REGEX = "(\\([0-9]{4}\\)|\\{[0-9]{4}\\}|\\[[0-9]{4}\\])";
    private static final String CD_REGEX = "cd([0-9])";
    private static final String ALTERNATIVE_YEAR_REGEX = "([0-9]{4})";
    private static final Pattern CD_PATTERN = Pattern.compile(CD_REGEX, Pattern.CASE_INSENSITIVE);

    private final FileUtil fileUtil;
    private final YearAnalyzer yearAnalyzer;

    public DefaultMoviePathAnalyzer(FileUtil fileUtil, YearAnalyzer yearAnalyzer) {
        this.fileUtil = fileUtil;
        this.yearAnalyzer = yearAnalyzer;
    }

    @Override
    public MoviePathInformation analyze(String path) {
        Integer year = yearAnalyzer.parseYear(path);
        Integer cd = getCd(path);
        return getMovieInformation(path, year, cd);
    }

    private MoviePathInformation getMovieInformation(final String path, final Integer year, final Integer cd) {
        String workingPath = path;
        Integer workingYear = year;
        if (cd != null) {
            workingPath = workingPath.replaceAll("(?i)"+CD_REGEX, "");
        }

        if (workingYear != null) {
            String tempWorkingPath = workingPath.replaceAll(YEAR_REGEX, "");
            if (tempWorkingPath.length() == workingPath.length()) {
                tempWorkingPath = workingPath.replaceAll(ALTERNATIVE_YEAR_REGEX, "");
            }
            String[] tempWorkingPathComponents = tempWorkingPath.split("\\" + File.separator);
            String fileName = tempWorkingPathComponents[tempWorkingPathComponents.length - 1];
            fileName = fileName.replace(fileUtil.extension(new File(path)), "").trim();
            if (fileName.length() == 0) {
                workingYear = null;
            } else {
                workingPath = tempWorkingPath;
            }

        }

        String[] parts = workingPath.split("\\"+ File.separator);
        String extension = fileUtil.extension(new File(path));
        return new DefaultMoviePathInformation(path, parts[parts.length-1].replace(extension, "").trim(), workingYear, cd);
    }

    private Integer getCd(String path) {
        Matcher cdMatcher = CD_PATTERN.matcher(path);
        if (cdMatcher.find()) {
            String group = cdMatcher.group(1);
            return Integer.parseInt(group);
        }
        return null;
    }

}
