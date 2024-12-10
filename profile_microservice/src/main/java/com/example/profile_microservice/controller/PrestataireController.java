package com.example.profile_microservice.controller;

import com.example.profile_microservice.entity.Prestataire;
import com.example.profile_microservice.entity.Review;
import com.example.profile_microservice.service.PrestataireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestataires")
public class PrestataireController {

    private final PrestataireService prestataireService;

    public PrestataireController(PrestataireService prestataireService) {
        this.prestataireService = prestataireService;
    }

    @GetMapping
    public List<Prestataire> getPrestataires(@RequestParam String categorie) {
        return prestataireService.getPrestatairesByCategorieDisponible(categorie);
    }

    @GetMapping("/categorie/{categorie}")
    public List<Prestataire> getPrestatairesByCategorie(@PathVariable String categorie) {
        return prestataireService.getPrestatairesByCategorieDisponible(categorie);
    }

    @PutMapping("/{id}/reserver")
    public ResponseEntity<String> reserverPrestataire(@PathVariable Long id) {
        Optional<Prestataire> prestataire = prestataireService.getPrestataireById(id);
        if (prestataire.isPresent()) {
            Prestataire selectedPrestataire = prestataire.get();
            if (selectedPrestataire.isDisponible()) {
                prestataireService.updateDisponibilite(id, false);
                return ResponseEntity.ok("Prestataire réservé avec succès 🎉 . ");
            } else {
                return ResponseEntity.status(400).body("Le prestataire n'est pas disponible.");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<Review> addReview(@PathVariable Long id, @RequestBody Review review) {
        Review savedReview = prestataireService.addReview(id, review);
        if (savedReview != null) {
            return ResponseEntity.ok(savedReview);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/{id}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        Optional<Prestataire> prestataire = prestataireService.getPrestataireById(id);
        if (prestataire.isPresent()) {
            double averageRating = prestataireService.getAverageRating(id);
            return ResponseEntity.ok(averageRating);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/reviews")
    public List<Review> getReviewsByPrestataire(@PathVariable Long id) {
        return prestataireService.getReviewsByPrestataireId(id);
    }
}
