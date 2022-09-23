package com.example.backend.exception;

public class PersonneNotFoundException extends  Exception{

    private Long identifiant;
    public PersonneNotFoundException(String message, Long identifiant) {
        super(message);
        this.identifiant = identifiant;
    }

    public Long getIdentifiant() {
        return identifiant;
    }
}
