package com.example.demo.service;

import com.example.demo.model.AuthorsBooks;

import java.util.List;
import java.util.Optional;

public interface AuthorsBooksService {
    void addAuthorsBooks(AuthorsBooks authorsBooks);

    List<AuthorsBooks> getAllAuthorsBookss();

    Optional<AuthorsBooks> getAuthorsBooksById(Long id);

    Optional<AuthorsBooks> putAuthorsBooksById(Long id, AuthorsBooks updatedAuthorsBooks);

    void deleteAuthorsBooksById(Long id);
}
