package com.example.backend.repository;

import com.example.backend.model.Personne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonneRepository extends CrudRepository<Personne, Long> {


    Optional<Personne> findPersonneByCIN(String cin);

}
