package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CinAlreadyExistsException extends RuntimeException {

    private String cin;
    public CinAlreadyExistsException(String message, String cin) {
        super(message);
        this.cin = cin;
    }

    public String getCin() {
        return cin;
    }
}
