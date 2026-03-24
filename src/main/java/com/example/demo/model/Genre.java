package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Entity(name="genre")
@Table(name="genre")
@Getter
@Setter
public class Genre {

    @Id
    @Column(name="id_genre")
    @GeneratedValue(generator = "id_genre_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_genre_seq", sequenceName = "id_genre_seq", initialValue = 1, allocationSize = 1)
    private Long idGenre;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "genre_name", nullable = false, unique = true)
    private String genreName;

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    private List<Book> books;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre() {

    }
}
