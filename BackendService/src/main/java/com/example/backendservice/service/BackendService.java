package com.example.backendservice.service;


import com.example.backendservice.AppError;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class BackendService {


    public String teamIdGenerator() {
        int firstNumber = (int) Math.round(Math.random() * 9);
        int secondNumber = (int) Math.round(Math.random() * 9);
        int thirdNumber = (int) Math.round(Math.random() * 9);
        int fourthNumber = (int) Math.round(Math.random() * 9);
        return firstNumber + Integer.toString(secondNumber)
                + thirdNumber + fourthNumber;
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
