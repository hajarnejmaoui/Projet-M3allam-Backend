package com.example.reservation_microservice.repository;

import com.example.reservation_microservice.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
        List<Article> findByCategory(String category);

}