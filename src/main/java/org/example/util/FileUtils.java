package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    public static List<Path> getListOfFiles() throws IOException {
        return Files.list(Paths.get("C:/inputFolder")).collect(Collectors.toList());
    }

}