package nl.stil4m.movielibrary.util.impl;

import nl.stil4m.movielibrary.util.FileUtil;

import java.io.File;

public class DefaultFileUtil implements FileUtil {

    public String extension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return fileName.substring(index);
    }

}
