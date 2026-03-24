package com.example.demo.dto;

import com.example.demo.model.UserAuthority;

public record AdminUserUpdateRequest(
        String login,
        String password,
        UserAuthority authority
) {}
