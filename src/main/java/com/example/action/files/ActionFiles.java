package com.example.action.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ActionFiles {

    private final Deque<Path> fileList = new ArrayDeque<>();
    private static final Logger log = LoggerFactory.getLogger(ActionFiles.class);
    @Value(value = "${directory.path.scanned}")
    private String scannedDirectoryPath;
    @Value(value = "${directory.path.target}")
    private String targetPath;
    @Value(value = "${directory.path.duplicate}")
    private String duplicateFile;


    public void doJob() {
        System.out.println("1");
        //просканировать дир, получить список файлов
        //пройтись по списку файлов , перенести файлы
        //пройтись по новой директории, распарсить каждый файл
        // перенести все файлы в завершено
        //move
    }


    public void scanDirectory() {
        try (Stream<Path> paths = Files.walk(Paths.get(scannedDirectoryPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        log.info("Found file: {}", file.getFileName());
                        fileList.addLast(file);
                    });
        } catch (IOException e) {
            log.error("Error scanning directory", e);
        }
    }

    public void moveFile(Path file) {
        Path targetDirectory = Paths.get(targetPath);
        Path targetPathMove = targetDirectory.resolve(file.getFileName());
        try {
            Files.move(file, targetPathMove);
            log.info("Moved file to: {}", targetPathMove);
        } catch (IOException e) {
            log.error("Error moving file: {}", file.getFileName(), e);
        }
    }

    public boolean checkForDuplicatesManually() {
        Set<Path> seen = new HashSet<>();
        for (Path element : fileList) {
            if (!seen.add(element)) {
                return true;
            }
        }
        return false;
    }

    public Path getLastFile() {
        return fileList.pollFirst();
    }
    public boolean fileListIsEmpty(){
        return fileList.isEmpty();
    }

    public Deque<Path> getFileList() {
        return fileList;
    }
}
