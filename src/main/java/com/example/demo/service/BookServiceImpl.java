package com.example.demo.service;

import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public void addBook(Book book) {
        Genre genre = book.getGenre();

        if (genre != null && genre.getIdGenre() != null) {
            genre = genreRepository.findById(genre.getIdGenre()).orElse(null);
        }

        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book putBookById(Long id, Book updatedBook) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (updatedBook.getGenre() != null && updatedBook.getGenre().getIdGenre() != null) {
            Genre genre = genreRepository.findById(updatedBook.getGenre().getIdGenre())
                    .orElse(null);
            bookToUpdate.setGenre(genre);
        }

        if (updatedBook.getBookTitle() != null) {
            bookToUpdate.setBookTitle(updatedBook.getBookTitle());
        }
        if (updatedBook.getDatePublic() != null) {
            bookToUpdate.setDatePublic(updatedBook.getDatePublic());
        }
        if (updatedBook.getPrice() != null) {
            bookToUpdate.setPrice(updatedBook.getPrice());
        }
        if (updatedBook.getStar() != null) {
            bookToUpdate.setStar(updatedBook.getStar());
        }
        if (updatedBook.getDescriptBook() != null) {
            bookToUpdate.setDescriptBook(updatedBook.getDescriptBook());
        }
        if (updatedBook.getCover() != null) {
            bookToUpdate.setCover(updatedBook.getCover());
        }

        return bookRepository.save(bookToUpdate);
    }

    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

}
