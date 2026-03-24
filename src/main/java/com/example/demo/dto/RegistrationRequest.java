package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotBlank(message = "Логин не может быть пустым")
    @Email(message = "Email имеет неверный формат")
    @jakarta.validation.constraints.Pattern(
            regexp = ".+@.+\\..+",
            message = "Email имеет неверный формат"
    )
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}


