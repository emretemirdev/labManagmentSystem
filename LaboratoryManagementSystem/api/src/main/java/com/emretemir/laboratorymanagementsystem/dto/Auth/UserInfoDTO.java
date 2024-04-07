package com.emretemir.laboratorymanagementsystem.dto.Auth;

import java.util.List;

public record UserInfoDTO(
        Long userId,
        String name,
        List<String> roles,
        Long hospitalId)
{
}