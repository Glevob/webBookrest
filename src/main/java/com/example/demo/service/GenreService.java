package com.example.demo.service;

import com.example.demo.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    void addGenre(Genre genre);

    List<Genre> getAllGenres();

    Optional<Genre> getGenreById(Long id);

    Optional<Genre> putGenreById(Long id, Genre updatedGenre);

    void deleteGenreById(Long id);
}
