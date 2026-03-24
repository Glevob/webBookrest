package com.example.demo.service;

import com.example.demo.exceptions.GlobalExceptionHandler;
import com.example.demo.model.Genre;
import com.example.demo.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;

    @Override
    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> putGenreById(Long id, Genre updatedGenre) {
        Optional<Genre> existingGenre = genreRepository.findById(id);
        if (existingGenre.isPresent()) {
            Genre genreToUpdate = existingGenre.get();
            if (updatedGenre.getGenreName() != null) {
                genreToUpdate.setGenreName(updatedGenre.getGenreName());
            }
            genreRepository.save(genreToUpdate);
        }
        return existingGenre;
    }

    @Override
    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }
}
