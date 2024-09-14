package com.example.action.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class ActionFiles {
    private static final Logger log = LoggerFactory.getLogger(ActionFiles.class);

    public List<Path> scanDirectory(String scannedDirectoryPath) {
        List<Path> fileList = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(scannedDirectoryPath))) {
            fileList = paths.filter(Files::isRegularFile)
                    .peek(file -> log.info("Found file: {}", file.getFileName()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error scanning directory", e);
        }

        return fileList;
    }


    public void moveFile(Path file,String targetPath) {
        Path targetDirectory = Paths.get(targetPath);
        Path targetPathMove = targetDirectory.resolve(file.getFileName());
        try {
            Files.move(file, targetPathMove);
            log.info("Moved file to: {}", targetPathMove);
        } catch (IOException e) {
            log.error("Error moving file: {}", file.getFileName(), e);
        }
    }


}
