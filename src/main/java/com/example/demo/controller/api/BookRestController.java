package com.example.demo.controller.api;

import com.example.demo.dto.BookPatchRequest;
import com.example.demo.dto.BookRequest;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.exceptions.BusinessValidationException;
import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Validated
public class BookRestController {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookRequest request) {
                LocalDate datePublic = request.getDatePublic();
        if (datePublic != null && datePublic.isAfter(LocalDate.now())) {
            throw new BusinessValidationException(
                    "datePublic must be in the past or present",
                    "Дата публикации не может быть в будущем"
            );
        }
                Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new BusinessValidationException(
                        "Genre with id=" + request.getGenreId() + " not found",
                        "Genre must exist in database"
                ));
        Book book = new Book(
                request.getBookTitle(),
                request.getDatePublic(),
                request.getPrice(),
                request.getStar(),
                request.getDescriptBook(),
                null,    // cover
                genre
        );
        Book saved = bookRepository.save(book);
        return ResponseEntity
                .created(URI.create("/api/books/" + saved.getIdBook()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request
    ) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new BusinessValidationException(
                        "Genre with id=" + request.getGenreId() + " not found",
                        "Genre must exist in database"
                ));

        if (request.getDatePublic() != null && request.getDatePublic().isAfter(LocalDate.now())) {
            throw new BusinessValidationException(
                    "datePublic must be in the past or present",
                    "Дата публикации не может быть в будущем"
            );
        }

        existing.setBookTitle(request.getBookTitle());
        existing.setDatePublic(request.getDatePublic());
        existing.setPrice(request.getPrice());
        existing.setStar(request.getStar());
        existing.setDescriptBook(request.getDescriptBook());
        existing.setGenre(genre);

        Book saved = bookRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchBook(@PathVariable Long id,@RequestBody BookPatchRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        if (request.getBookTitle() != null) {
            existing.setBookTitle(request.getBookTitle());
        }
        if (request.getDatePublic() != null) {
            if (request.getDatePublic().isAfter(LocalDate.now())) {
                throw new BusinessValidationException(
                        "datePublic must be in the past or present",
                        "Дата публикации не может быть в будущем"
                );
            }
            existing.setDatePublic(request.getDatePublic());
        }
        if (request.getPrice() != null) {existing.setPrice(request.getPrice());}
        if (request.getStar() != null) {existing.setStar(request.getStar());}
        if (request.getDescriptBook() != null) {existing.setDescriptBook(request.getDescriptBook());}
        if (request.getGenreId() != null) {
            Genre genre = genreRepository.findById(request.getGenreId())
                    .orElseThrow(() -> new BusinessValidationException(
                            "Genre with id=" + request.getGenreId() + " not found",
                            "Genre must exist in database"
                    ));
            existing.setGenre(genre);
        }
        Book saved = bookRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
