package com.example.demo.controller;

import com.example.demo.model.Genre;
import com.example.demo.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    ResponseEntity<Void> addGenre(@RequestBody @Valid Genre genre,
                                 BindingResult bindingResult){
        genreService.addGenre(genre);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    ResponseEntity<List<Genre>> getAllGenres(){
        return ResponseEntity.ok(genreService.getAllGenres());

    }

    @GetMapping("/{id}")
    ResponseEntity<Genre> getGenreById(@PathVariable Long id){
        Optional<Genre> genreOptional = genreService.getGenreById(id);
        return genreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Genre> updateGenreById(@PathVariable Long id, @RequestBody @Validated Genre updatedGenre) {
        Optional<Genre> updatedGenreOptional = genreService.putGenreById(id, updatedGenre);
        return updatedGenreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGenreById(@PathVariable Long id){
        genreService.deleteGenreById(id);
        return ResponseEntity.ok().build();
    }
}
