package com.example.backend.service;


import com.example.backend.BackendApplication;
import com.example.backend.exception.CinAlreadyExistsException;
import com.example.backend.exception.PersonneNotFoundException;
import com.example.backend.model.Personne;
import com.example.backend.repository.PersonneRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

import static com.example.backend.constant.Constant.PERSON_IMAGE_DIR;

@Service
public class PersonneService {


    private final PersonneRepository personneRepository;
    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public Personne saveOrUpdate(Personne personne) throws CinAlreadyExistsException {

        if (personneRepository.findPersonneByCIN(personne.getCIN()).isPresent()) {
            throw new CinAlreadyExistsException("CIN déjà existé", personne.getCIN());
        } else {
            return personneRepository.save(personne);
        }



    }


    public Optional<Personne> getPersonById(Long id) throws PersonneNotFoundException {


        if (!personneRepository.findById(id).isPresent()) {
            throw new PersonneNotFoundException("Personne introuvable", id);
        } else {
            return personneRepository.findById(id);
        }


    }


}

