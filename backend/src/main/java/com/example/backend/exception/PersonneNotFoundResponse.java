package com.example.backend.exception;

public class PersonneNotFoundResponse {

    private String identifiant;

    public PersonneNotFoundResponse(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getIdentifiant() {
        return identifiant;
    }


    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
}
