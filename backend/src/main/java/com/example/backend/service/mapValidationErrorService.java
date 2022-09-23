package com.example.backend.service;

import com.example.backend.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Service
public class mapValidationErrorService {

    public static ResponseEntity<?> MapValidationService(BindingResult result){

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("errors",errorMap))
                            .message("la création a échoué")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

        return null;

    }
}
