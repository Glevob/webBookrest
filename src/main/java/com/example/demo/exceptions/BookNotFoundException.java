package com.example.demo.exceptions;

public class BookNotFoundException extends RuntimeException {

    private final Long id;

    public BookNotFoundException(Long id) {
        super("Книга с id " + id + " не найдена");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
