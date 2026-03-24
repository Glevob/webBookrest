package com.example.demo.service;

import com.example.demo.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void addBook(Book book);

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book putBookById(Long id, Book updatedBook);

    void deleteBookById(Long id);
}
