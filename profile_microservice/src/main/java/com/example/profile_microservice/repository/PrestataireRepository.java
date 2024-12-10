package com.example.profile_microservice.repository;

import com.example.profile_microservice.entity.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {
    List<Prestataire> findByCategorieAndDisponible(String categorie, boolean disponible);
    List<Prestataire> findByCategorie(String categorie);  // Add this method

}