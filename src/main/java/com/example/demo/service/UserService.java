package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    void registration(String login, String password);
    List<User> getAllUsers();
}
