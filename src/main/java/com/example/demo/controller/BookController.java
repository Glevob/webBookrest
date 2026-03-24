//package com.example.demo.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import com.example.demo.model.Book;
//import com.example.demo.service.BookService;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/books")
//public class BookController {
//    private final BookService bookService;
//
//    @PostMapping
//    ResponseEntity<Void> addBook(@RequestBody @Valid Book book,
//                                     BindingResult bindingResult){
//        bookService.addBook(book);
//        return ResponseEntity.ok().build();
//
//    }
//
//    @GetMapping
//    ResponseEntity<List<Book>> getAllBooks(){
//        return ResponseEntity.ok(bookService.getAllBooks());
//
//    }
//
//    @GetMapping("/{id}")
//    ResponseEntity<Book> getBookById(@PathVariable Long id){
//        Optional<Book> bookOptional = bookService.getBookById(id);
//        return bookOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/{id}")
//    ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody @Validated Book updatedBook) {
//        Optional<Book> updatedBookOptional = bookService.putBookById(id, updatedBook);
//        return updatedBookOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    ResponseEntity<Void> deleteBookById(@PathVariable Long id){
//        bookService.deleteBookById(id);
//        return ResponseEntity.ok().build();
//    }
//}
