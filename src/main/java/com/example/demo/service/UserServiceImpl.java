package com.example.demo.service;

import com.example.demo.exceptions.LoginAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.User;
import com.example.demo.model.UserAuthority;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(String login, String password) {
        if (userRepository.findByLogin(login).isEmpty()) {
            User user = userRepository.save(
                    new User()
                            .setIdUser(null)
                            .setLogin(login)
                            .setPassword(passwordEncoder.encode(password))
            );
            userRoleRepository.save(new UserRole(null, UserAuthority.USER, user));
        } else {
            throw new LoginAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

