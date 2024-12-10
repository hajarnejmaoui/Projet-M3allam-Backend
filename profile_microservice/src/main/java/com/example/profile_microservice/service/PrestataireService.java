package com.example.profile_microservice.service;

import com.example.profile_microservice.entity.Prestataire;
import com.example.profile_microservice.entity.Review;
import com.example.profile_microservice.repository.PrestataireRepository;
import com.example.profile_microservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestataireService {

    private final PrestataireRepository prestataireRepository;
    private final ReviewRepository reviewRepository;

    public PrestataireService(PrestataireRepository prestataireRepository, ReviewRepository reviewRepository) {
        this.prestataireRepository = prestataireRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Prestataire> getPrestatairesByCategorieDisponible(String categorie) {
        return prestataireRepository.findByCategorieAndDisponible(categorie, true);
    }

    public List<Prestataire> getPrestatairesByCategorie(String categorie) {
        return prestataireRepository.findByCategorie(categorie);
    }
    public double getAverageRating(Long prestataireId) {
        List<Review> reviews = reviewRepository.findByPrestataireId(prestataireId);
        if (reviews.isEmpty()) {
            return 0.0; // Retourne 0 si aucun avis n'est disponible
        }
        return reviews.stream()
                .mapToInt(Review::getEtoiles)
                .average()
                .orElse(0.0);
    }


    public Optional<Prestataire> getPrestataireById(Long id) {
        return prestataireRepository.findById(id);
    }

    public Prestataire updateDisponibilite(Long id, boolean disponible) {
        Optional<Prestataire> prestataire = prestataireRepository.findById(id);
        if (prestataire.isPresent()) {
            Prestataire prestataireToUpdate = prestataire.get();
            prestataireToUpdate.setDisponible(disponible);
            return prestataireRepository.save(prestataireToUpdate);
        }
        return null;
    }

    public Review addReview(Long prestataireId, Review review) {
        Optional<Prestataire> prestataireOpt = prestataireRepository.findById(prestataireId);
        if (prestataireOpt.isPresent()) {
            Prestataire prestataire = prestataireOpt.get();
            review.setPrestataire(prestataire);
            Review savedReview = reviewRepository.save(review);

            // Recalculer la moyenne des étoiles
            double averageRating = getAverageRating(prestataireId);
            prestataire.setReview(averageRating); // Mettre à jour la colonne review
            prestataireRepository.save(prestataire);

            return savedReview;
        }
        return null;
    }



    public List<Review> getReviewsByPrestataireId(Long prestataireId) {
        return reviewRepository.findByPrestataireId(prestataireId);
    }
}
