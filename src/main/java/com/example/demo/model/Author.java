package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Entity(name="author")
@Table(name="author")
@Getter
@Setter
public class Author {

    @Id
    @Column(name="id_author")
    @GeneratedValue(generator = "id_author_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_author_seq", sequenceName = "id_author_seq", initialValue = 1, allocationSize = 1)
    private Long idAuthor;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]+$", message = "Допустимы только буквы без пробелов и знаков")
    @Size(min = 2, max = 100)
    @Column(name="author_name")
    private String authorName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]+$", message = "Допустимы только буквы без пробелов и знаков")
    @Size(min = 2, max = 100)
    @Column(name="author_surname")
    private String authorSurname;

    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]*$",
            message = "Допустимы только буквы без пробелов и знаков")
    @Column(name="author_middlename")
    private String authorMiddlename;

    @Email
    @NotBlank
    @Size(max = 150)
    @Column(name="author_email", nullable = false, unique = true)
    private String authorEmail;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<AuthorsBooks> authorsBookss;

    public Author(String authorName, String authorSurname, String authorMiddlename, String authorEmail) {
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.authorMiddlename = authorMiddlename;
        this.authorEmail = authorEmail;
    }

    public Author() {

    }
}
