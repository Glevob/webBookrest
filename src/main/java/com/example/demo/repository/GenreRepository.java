package com.example.demo.repository;

import com.example.demo.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface GenreRepository extends JpaRepository<Genre, Long>, PagingAndSortingRepository<Genre, Long> {
}
