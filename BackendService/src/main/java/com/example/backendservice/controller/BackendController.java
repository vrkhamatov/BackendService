package com.example.backendservice.controller;

import com.example.backendservice.ResourceNotFoundException;
import com.example.backendservice.models.MetaData;
import com.example.backendservice.repositories.MetaDataRepository;
import com.example.backendservice.serelization.JsonSerelizator;
import com.example.backendservice.service.BackendService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BackendController {

    private final BackendService backendService;

    private final MetaDataRepository metaDataRepository;

    private final JsonSerelizator jsonSerelizator = new JsonSerelizator();



    @Autowired
    Environment env; //get path to file directory from command line

    // command: //java -jar target/BackendService-0.0.1-SNAPSHOT.jar --path=C:\Users\khama\Desktop\LetiPrint\BackendService\src\main\resources\storage
   @GetMapping("/downloadFile")
   @ResponseBody
   @ExceptionHandler
   private ResponseEntity<?> getFile(@RequestParam String fileId) {
       try {
           MetaData metaData = metaDataRepository.getMetaDataByFileId(fileId);
           return new ResponseEntity<>(jsonSerelizator.serializeJsonForIoTService(metaData), HttpStatus.OK);
       }
       catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
   }

    @PostMapping("/metaData")
    public String getUsersId(@RequestBody String metaData) {
        String fileId = backendService.teamIdGenerator(metaDataRepository.getFileIds());
        metaDataRepository.save(jsonSerelizator.deserializeMetaDates(metaData,fileId));
        return fileId;
    }



}
