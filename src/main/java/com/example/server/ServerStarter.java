package com.example.server;

import com.example.action.files.FileActions;
import com.example.file.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@EnableScheduling
@Component
public class ServerStarter {
    private static final Logger log = LoggerFactory.getLogger(ServerStarter.class);
    @Value(value = "${directory.path.scanned}")
    private String scannedDirectoryPath;
    @Value(value = "${directory.path.target}")
    private String targetPath;
    @Value(value = "${directory.path.duplicate}")
    private String duplicatePath;
    @Value(value = "${directory.path.successful}")
    private String successfulPath;
    private final FileActions fileActions;
    private final FileProcessor fileProcessor;

    @Autowired
    public ServerStarter(FileActions fileActions, FileProcessor fileProcessor) {
        this.fileActions = fileActions;
        this.fileProcessor = fileProcessor;
    }

    Deque<Path> inProgressFiles = new ArrayDeque<>();
    Deque<Path> finishedFiles = new ArrayDeque<>();


    @Scheduled(fixedDelayString = "${scan.millis}")
    public void scanInboxTask() {
        //заглушка для проверки на запуск приложения после неправильного завершения
//        List<Path> fileListSuccessful = fileActions.scanDirectory(targetPath);
//        if (inProgressFiles.isEmpty()) {
//        }
        List<Path> inboxFileList = fileActions.scanDirectory(scannedDirectoryPath);
        for (Path pathToProcess : inboxFileList) {
            try {
                fileActions.moveFile(pathToProcess, targetPath);
            } catch (FileAlreadyExistsException e) {
                log.info("File already exists:{}", pathToProcess.getFileName());
                try {
                    String currentTimeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"));
                    Path duplicateTargetDir = Paths.get(duplicatePath, currentTimeString);
                    /*
                    todo: перенести в fileAction сделать опциальным
                     */
                    Files.createDirectories(duplicateTargetDir);
                    fileActions.moveFile(pathToProcess, String.valueOf(Paths.get(duplicateTargetDir.toString())));
                } catch (IOException ioException) {
                    log.error("fatal error:{}", ioException.toString());
                }
            }

            inProgressFiles.add(pathToProcess.getFileName());
        }
        inboxFileList.clear();


    }

    @Scheduled(fixedDelay = 100)
    public void processFilesTask() {
        while (!inProgressFiles.isEmpty()) {
            Path path = inProgressFiles.pop();
            fileProcessor.processFile(Paths.get(targetPath, path.toString()));
            finishedFiles.add(path);
        }

    }

    @Scheduled(fixedDelay = 100)
    public void moveFinishedFilesTask() {
        while (!finishedFiles.isEmpty()) {
            Path path = finishedFiles.pop();
            try {
                fileActions.moveFile(Paths.get(targetPath,path.toString()), successfulPath);
            } catch (FileAlreadyExistsException e) {
                log.error("fatal error:{}", e.toString());
            }

        }

    }
}