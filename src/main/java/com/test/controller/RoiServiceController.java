package com.test.controller;

import com.test.model.InputData;
import com.test.model.OutputData;
import com.test.service.RoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api")
public class RoiServiceController {

   @Autowired
   private RoiService roiService;

   @RequestMapping(value = "/calculateRoi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<OutputData>> calculateRoi(@RequestBody InputData inputData) throws InterruptedException, ExecutionException, URISyntaxException, IOException {
      return new ResponseEntity<>(roiService.calculateRoi(inputData), HttpStatus.OK);
   }
}
