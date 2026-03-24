package com.example.demo.controller;

import com.example.demo.model.AuthorsBooks;
import com.example.demo.service.AuthorsBooksService;
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
@RequestMapping("/authors_bookss")
public class AuthorsBooksController {
    private final AuthorsBooksService authorsBooksService;

    @PostMapping
    ResponseEntity<Void> addAuthorsBooks(@RequestBody @Valid AuthorsBooks authorsBooks,
                                     BindingResult bindingResult){
        authorsBooksService.addAuthorsBooks(authorsBooks);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    ResponseEntity<List<AuthorsBooks>> getAllAuthorsBookss(){
        return ResponseEntity.ok(authorsBooksService.getAllAuthorsBookss());

    }

    @GetMapping("/{id}")
    ResponseEntity<AuthorsBooks> getAuthorsBooksById(@PathVariable Long id){
        Optional<AuthorsBooks> authorsBooksOptional = authorsBooksService.getAuthorsBooksById(id);
        return authorsBooksOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<AuthorsBooks> updateAuthorsBooksById(@PathVariable Long id, @RequestBody @Validated AuthorsBooks updatedAuthorsBooks) {
        Optional<AuthorsBooks> updatedAuthorsBooksOptional = authorsBooksService.putAuthorsBooksById(id, updatedAuthorsBooks);
        return updatedAuthorsBooksOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAuthorsBooksById(@PathVariable Long id){
        authorsBooksService.deleteAuthorsBooksById(id);
        return ResponseEntity.ok().build();
    }
}
