package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "Название не может быть пустым")
    private String bookTitle;

    @NotNull
    private LocalDate datePublic;

    @NotNull
    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Integer price;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer star;

    @NotBlank
    private String descriptBook;

    @NotNull
    private Long genreId;

}
