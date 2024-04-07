package com.emretemir.laboratorymanagementsystem.dto.Auth;

import com.emretemir.laboratorymanagementsystem.model.Role;

import java.util.Set;


public record CreateUserRequest(
        Long userId,
        String name,
        String username,
        String password,
        Long hospitalId,
        Set<Role> authorities
){
}