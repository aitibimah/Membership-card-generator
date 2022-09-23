package com.example.backend.validator;


import com.example.backend.model.Personne;
import com.example.backend.util.Util;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
@Component
public class PersonneValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Personne personne = (Personne) target;

        if(personne.getCIN().length() <= 7 && personne.getCIN().length() <= 8){
            errors.rejectValue("CIN","Length", "CIN doit comporter au moins 7 caractères");
        }

        if(LocalDate.now().isBefore(LocalDate.of(personne.getDateDeNaissace().getYear(), personne.getDateDeNaissace().getMonth(), personne.getDateDeNaissace().getDay()))){
            errors.rejectValue("dateDeNaissace","DateInFuture", "La date de naissance doit être dans le passé");
        }


        if (!Util.isArabic(personne.getNomAr())){
            errors.rejectValue("nomAr","notInArabic", "Le nom en arabe doit en des caractères arabe");

        }

        if (!Util.isArabic(personne.getPrenomAr())){
            errors.rejectValue("prenomAr","notInArabic", "Le prenom en arabe doit en des caractères arabe");

        }

    }
}
