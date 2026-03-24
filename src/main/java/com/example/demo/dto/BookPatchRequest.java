package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookPatchRequest {
    private String bookTitle;
    private LocalDate datePublic;
    private Integer price;
    private Integer star;
    private String descriptBook;
    private Long genreId;

}

