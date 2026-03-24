package com.example.demo.repository;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorsBooks;
import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AuthorsBooksRepository extends JpaRepository<AuthorsBooks, Long>, PagingAndSortingRepository<AuthorsBooks, Long> {
    void deleteAllByBook(Book book);
    List<AuthorsBooks> findAllByBook(Book book);
    void deleteAllByAuthor(Author author);
}
