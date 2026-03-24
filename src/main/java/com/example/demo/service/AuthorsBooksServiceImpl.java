package com.example.demo.service;

import com.example.demo.exceptions.GlobalExceptionHandler;
import com.example.demo.model.*;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.AuthorsBooksRepository;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorsBooksServiceImpl implements AuthorsBooksService{
    private final GlobalExceptionHandler globalExceptionHandler;
    private final AuthorsBooksRepository authorsBooksRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void addAuthorsBooks(AuthorsBooks authorsBooks) {
        Book book = authorsBooks.getBook();
        Author author = authorsBooks.getAuthor();

        if (book != null && book.getIdBook() != null) {
            book = bookRepository.findById(book.getIdBook()).orElse(null);
        }
        if (author != null && author.getIdAuthor() != null) {
            author = authorRepository.findById(author.getIdAuthor()).orElse(null);
        }

        authorsBooks.setBook(book);
        authorsBooks.setAuthor(author);

        authorsBooksRepository.save(authorsBooks);
    }

    @Override
    public List<AuthorsBooks> getAllAuthorsBookss() {
        return authorsBooksRepository.findAll();
    }

    @Override
    public Optional<AuthorsBooks> getAuthorsBooksById(Long id) {
        return authorsBooksRepository.findById(id);
    }

    @Override
    public Optional<AuthorsBooks> putAuthorsBooksById(Long id, AuthorsBooks updatedAuthorsBooks) {
        Optional<AuthorsBooks> existingAuthorsBooks = authorsBooksRepository.findById(id);
        if(existingAuthorsBooks.isPresent()){

            AuthorsBooks authorsBooksToUpdate = existingAuthorsBooks.get();
            if(updatedAuthorsBooks.getBook() != null && updatedAuthorsBooks.getBook().getIdBook() != null) {
                Book book = bookRepository.findById(updatedAuthorsBooks.getBook().getIdBook()).orElse(null);
                authorsBooksToUpdate.setBook(book);
            }
            if(updatedAuthorsBooks.getAuthor() != null && updatedAuthorsBooks.getAuthor().getIdAuthor() != null) {
                Author author = authorRepository.findById(updatedAuthorsBooks.getAuthor().getIdAuthor()).orElse(null);
                authorsBooksToUpdate.setAuthor(author);
            }

            if(updatedAuthorsBooks.getAuthorRole() != null) {
                authorsBooksToUpdate.setAuthorRole(updatedAuthorsBooks.getAuthorRole());
            }

            authorsBooksRepository.save(authorsBooksToUpdate);
        }
        return existingAuthorsBooks;
    }

    @Override
    public void deleteAuthorsBooksById(Long id) {
        authorsBooksRepository.deleteById(id);
    }
}
