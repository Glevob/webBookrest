package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.model.UserAuthority;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String login = "ad@ad.com";
        String rawPassword = "asdasd";

        // если такой пользователь уже есть — ничего не делаем
        if (userRepository.findByLogin(login).isPresent()) {
            return;
        }

        // создаём пользователя
        User user = new User()
                .setIdUser(null)
                .setLogin(login)
                .setPassword(passwordEncoder.encode(rawPassword));

        user = userRepository.save(user);

        // выдаём ему роль FULL
        UserRole fullRole = new UserRole(null, UserAuthority.FULL, user);
        userRoleRepository.save(fullRole);
    }
}
