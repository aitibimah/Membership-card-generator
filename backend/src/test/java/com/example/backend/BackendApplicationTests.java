package com.example.backend;

import com.example.backend.exception.PersonneNotFoundException;
import com.example.backend.model.Personne;
import com.example.backend.service.PersonneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private PersonneService personneService;

    @Test
    void contextLoads() throws PersonneNotFoundException {
        Optional<Personne> personne = personneService.getPersonById(10L);

        System.out.println(personne.get().getImage());
    }

}
