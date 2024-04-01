package com.emretemir.laboratorymanagementsystem.dto.Auth;

public record AuthRequest(
    String username,
    String password,
    String hospitalId
) {

}
