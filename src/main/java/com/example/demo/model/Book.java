package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@Entity(name="book")
@Table(name="book")
@Getter
@Setter
public class Book {

    @Id
    @Column(name="id_book")
    @GeneratedValue(generator = "id_book_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_book_seq", sequenceName = "id_book_seq", initialValue = 1, allocationSize = 1)
    private Long idBook;

    @NotBlank
    @Size(min = 2, max = 200)
    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @NotNull
    @PastOrPresent
    @Column(name = "date_public", nullable = false)
    private LocalDate datePublic;

    @NotNull
    @Min(1)
    @Column(name="price", nullable = false)
    private Integer price;

    @NotNull
    @Min(0)
    @Max(5)
    @Column(name="star", nullable = false)
    private Integer star;

    @Size(max = 40000)
    @Column(name="desript_book", length = 40000)
    private String descriptBook;

    @Column(name="cover")
    private String cover;

    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<AuthorsBooks> authorsBookss;

    public Book(String bookTitle, LocalDate datePublic, Integer price, Integer star/*, Integer publicationCost, Integer authorFee*/, String descriptBook, String cover, Genre genre) {
        this.bookTitle = bookTitle;
        this.datePublic = datePublic;
        this.price = price;
        this.star = star;
        this.descriptBook = descriptBook;
        this.cover = cover;
        this.genre = genre;
    }

    public Book() {

    }
}
