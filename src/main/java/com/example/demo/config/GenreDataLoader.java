package com.example.demo.config;

import com.example.demo.model.Genre;
import com.example.demo.repository.GenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreDataLoader implements CommandLineRunner {

    private final GenreRepository genreRepository;

    public GenreDataLoader(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(String... args) {
        List<String> defaultGenres = List.of(
                "Сказка",
                "Рассказ",
                "Триллер",
                "Ужас",
                "Биография",
                "Художественная литература",
                "Психология",
                "Бизнес",
                "Детское",
                "Подростковое",
                "Здоровье",
                "Спорт",
                "Хобби"
        );

        for (String genreName : defaultGenres) {
            boolean exists = genreRepository
                    .findAll()
                    .stream()
                    .anyMatch(g -> g.getGenreName().equalsIgnoreCase(genreName));

            if (!exists) {
                genreRepository.save(new Genre(genreName));
            }
        }
    }
}
