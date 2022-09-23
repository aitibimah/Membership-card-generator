package com.example.backend.model;


import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="personne")
@Data
public class Personne implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "le nom est un champ obligatoire")
    private String nom;
    @NotBlank(message = "le nom est un champ obligatoire")
    private String nomAr;
    @NotBlank(message = "le prenom est un champ obligatoire")
    private String prenom;
    @NotBlank(message = "le prenom est un champ obligatoire")
    private String prenomAr;

    @NotBlank(message = "le CIN est un champ obligatoire")
    @Column(unique = true)
    private String CIN;
    private String profession;
    private Date dateDeNaissace;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private TypeCarte typeCarte;

    private String image;

    @Transient
    private String base64Image;
    private Date dateDeCreation;
    private Date dateDeMiseJour;

    public Personne() {

    }

    @PrePersist
    protected void onCreate(){
        this.dateDeCreation = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.dateDeMiseJour = new Date();
    }


    public JSONObject toJSON(){

        JSONObject jPersonne = new JSONObject();
        jPersonne.put("Prenom", this.prenom);
        jPersonne.put("Nom", this.nom);
        jPersonne.put("CIN", this.CIN);
        jPersonne.put("Date de Naissance", this.dateDeNaissace);
        return jPersonne;
    }

}
