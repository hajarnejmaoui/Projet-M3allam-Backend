package com.example.profile_microservice.repository;

import com.example.profile_microservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPrestataireId(Long prestataireId);
}
