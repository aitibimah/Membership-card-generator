package com.example.backend.exception;

public class CinAlreadyExistsResponse {

    private String cin;

    public CinAlreadyExistsResponse(String cin) {
        this.cin = cin;
    }

    public String getCin() {
        return this.cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
