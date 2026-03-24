package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {
    boolean existsByAuthorEmail(String authorEmail);
    Optional<Author> findByAuthorEmail(String authorEmail);
}
