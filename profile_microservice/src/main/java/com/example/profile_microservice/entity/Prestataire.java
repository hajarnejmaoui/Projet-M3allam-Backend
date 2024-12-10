package com.example.profile_microservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prestataire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String telephone;
    private String categorie;
    private int commandesTraitees;
    private double review;
    private boolean disponible;  // Cette propriété sera utilisée pour la disponibilité générale

    @Transient
    private boolean estReserve;  // Calculé dynamiquement, ne persiste pas dans la base
}
