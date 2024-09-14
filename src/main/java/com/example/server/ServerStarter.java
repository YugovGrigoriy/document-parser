package com.example.server;

import com.example.action.files.ActionFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ServerStarter {
    ActionFiles actionFiles;

    @Autowired
    public ServerStarter(ActionFiles actionFiles) {
        this.actionFiles = actionFiles;
    }


    @Scheduled(fixedRateString = "${scan.millis}")
    public void startServer() {
        actionFiles.scanDirectory();

        while (!actionFiles.fileListIsEmpty()) {
            if(actionFiles.checkForDuplicatesManually()) {
                actionFiles.moveFile(actionFiles.getLastFile());
            }
        }
    }
}
