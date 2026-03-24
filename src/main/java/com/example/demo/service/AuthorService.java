package com.example.demo.service;

import com.example.demo.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    void addAuthor(Author author);

    List<Author> getAllAuthors();

    Optional<Author> getAuthorById(Long id);

    Optional<Author> putAuthorById(Long id, Author updatedAuthor);

    void deleteAuthorById(Long id);
}
