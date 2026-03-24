package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    ResponseEntity<Void> addAuthor(@RequestBody @Valid Author author,
                                  BindingResult bindingResult){
        authorService.addAuthor(author);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());

    }

    @GetMapping("/{id}")
    ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        Optional<Author> authorOptional = authorService.getAuthorById(id);
        return authorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Author> updateAuthorById(@PathVariable Long id, @RequestBody @Validated Author updatedAuthor) {
        Optional<Author> updatedAuthorOptional = authorService.putAuthorById(id, updatedAuthor);
        return updatedAuthorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }
}
