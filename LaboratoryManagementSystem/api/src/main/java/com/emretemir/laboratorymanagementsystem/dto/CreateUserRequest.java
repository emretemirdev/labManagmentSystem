package com.emretemir.laboratorymanagementsystem.dto;

import com.emretemir.laboratorymanagementsystem.model.Role;
import lombok.Builder;

import java.util.Set;


@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
){
}