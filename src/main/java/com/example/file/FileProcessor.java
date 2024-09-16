package com.example.file;

import com.example.file.dto.PaymentEntryDto;
import com.example.repo.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileProcessor {
    Parser parser;
    Validation validation;
    FileRepository repository;


    @Autowired
    public FileProcessor(Parser parser, Validation validation, FileRepository repository) {
        this.parser = parser;
        this.validation = validation;
        this.repository = repository;

    }

    private static final Logger log = LoggerFactory.getLogger(FileProcessor.class);

    public void processFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = parser.getParseArr(line);
                try{
                    validation.validation(arr);
                    if (arr.length != 5) {
                        throw new IllegalArgumentException("failed to parse line:");
                    }
                    PaymentEntryDto paymentEntryDto = PaymentEntryDto.builder()
                            .recordNumber(arr[0])
                            .paymentID(arr[1])
                            .companyName(arr[2])
                            .payerINN(arr[3])
                            .amount(arr[4])
                            .fileName(path.getFileName().toString())
                            .build();
                    // validation.validate2(paymentEntryDto);
                    repository.save(paymentEntryDto);
                    log.info("successfully saved file id DB:{}",paymentEntryDto.getFileName());
                }catch (IllegalArgumentException e){
                    repository.insertExample(line);
                }


            }
        } catch (IOException e) {
            log.error("read file:{}, error", path);
            log.error(e.toString());
        }
    }
}




