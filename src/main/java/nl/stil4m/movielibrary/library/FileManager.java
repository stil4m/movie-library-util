package nl.stil4m.movielibrary.library;

import java.io.File;
import java.util.List;

public interface FileManager {

    List<File> listFiles(File dir);

    Boolean isFile(File file);

    Boolean hasExtension(File file, String... extensions);
}
