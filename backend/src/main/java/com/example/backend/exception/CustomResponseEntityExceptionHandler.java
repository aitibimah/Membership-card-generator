package com.example.backend.exception;

import com.example.backend.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ CinAlreadyExistsException.class })
    public final ResponseEntity<Response> handleCinAlreadyExists(CinAlreadyExistsException ex, WebRequest request){
        CinAlreadyExistsResponse exceptionResponse = new CinAlreadyExistsResponse(ex.getMessage());
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("errors",exceptionResponse))
                        .message("la création a échoué")
                        .developerMessage("la personne avec le CIN "+ ex.getCin() +" déjà existé.")
                        .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());

    }


    @ExceptionHandler({ PersonneNotFoundException.class })
    public final ResponseEntity<Response> handlePersonneNotFound(PersonneNotFoundException ex, WebRequest request){
        PersonneNotFoundResponse exceptionResponse = new PersonneNotFoundResponse(ex.getMessage());
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Personne introuvable")
                        .developerMessage("la personne avec l'identifiant "+ ex.getIdentifiant() +" n'existe pas")
                        .status(HttpStatus.NOT_FOUND).statusCode(HttpStatus.NOT_FOUND.value())
                        .build());

    }

}
