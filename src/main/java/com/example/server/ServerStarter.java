package com.example.server;

import com.example.action.files.ActionFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Component
public class ServerStarter {
    @Value(value = "${directory.path.scanned}")
    private String scannedDirectoryPath;
    @Value(value = "${directory.path.target}")
    private String targetPath;
    @Value(value = "${directory.path.duplicate}")
    private String duplicateFile;
    ActionFiles actionFiles;

    @Autowired
    public ServerStarter(ActionFiles actionFiles) {
        this.actionFiles = actionFiles;
    }


    @Scheduled(fixedRateString = "${scan.millis}")
    public void workServer() {
        List<Path>fileList=actionFiles.scanDirectory(scannedDirectoryPath);
        for (Path path : fileList) {
            actionFiles.moveFile(path, targetPath);
        }
    }
}
