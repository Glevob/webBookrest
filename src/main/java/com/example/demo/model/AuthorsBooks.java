package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity(name="authors_books")
@Table(name="authors_books")
@Getter
@Setter
public class AuthorsBooks {

    @Id
    @Column(name="id_authors_books")
    @GeneratedValue(generator = "id_authors_books_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_authors_books_seq", sequenceName = "id_authors_books_seq", initialValue = 1, allocationSize = 1)
    private Long idAuthorsBooks;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    @NotBlank
    @Size(max = 100)
    @Column(name = "author_role", nullable = false)
    private String authorRole;

    public AuthorsBooks(Book book, Author author, String authorRole) {
        this.book = book;
        this.author = author;
        this.authorRole = authorRole;
    }

    public AuthorsBooks() {

    }
}
