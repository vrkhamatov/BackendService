package com.example.backendservice.service;


import com.example.backendservice.AppError;
import com.example.backendservice.models.MetaData;
import com.example.backendservice.repositories.MetaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BackendService {

    private final MetaDataRepository metaDataRepository;


    public String teamIdGenerator(List<Short> listOfFileId) {
        int firstNumber = (int) Math.round(Math.random() * 9);
        int secondNumber = (int) Math.round(Math.random() * 9);
        int thirdNumber = (int) Math.round(Math.random() * 9);
        int fourthNumber = (int) Math.round(Math.random() * 9);
        String id =  firstNumber + Integer.toString(secondNumber)
                + thirdNumber + fourthNumber;
        for (int i = 0; i < listOfFileId.size(); i++) {
            if (Objects.equals(id, listOfFileId.get(i))) {
                id = teamIdGenerator(listOfFileId);

            }
        }
        return id;
    }



    public ResponseEntity<?> getFileFromLocalDirectory(String envResponse, String filename) {
        File file = new File(envResponse + "/" + filename);
        InputStreamResource i;
        try {
            i = new InputStreamResource(new FileInputStream(file)); //response if file was founded
            return ResponseEntity.ok()
                    .header("fileName", file.getName())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(i);
        } catch (FileNotFoundException e) { //response if file wasn't founded
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "File with filename " + filename + " not found"),
                    HttpStatus.NOT_FOUND);
        }

    }

}
