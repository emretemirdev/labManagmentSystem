package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.dto.Auth.AuthRequest;
import com.emretemir.laboratorymanagementsystem.dto.Auth.CreateUserRequest;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.service.JWTService;
import com.emretemir.laboratorymanagementsystem.service.UserService;
import com.emretemir.laboratorymanagementsystem.core.constants.ApiPathConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("checkstyle:LineLength")
@RestController
@RequestMapping(ApiPathConstants.USER_BASE_URL)
@Slf4j
public class UserController {

    @SuppressWarnings("checkstyle:JavadocVariable")
    private final UserService userService;

    @SuppressWarnings("checkstyle:JavadocVariable")
    private final JWTService jwtService;

    @SuppressWarnings("checkstyle:JavadocVariable")
    private final AuthenticationManager authenticationManager;

    @SuppressWarnings({"checkstyle:HiddenField", "checkstyle:FinalParameters", "checkstyle:MissingJavadocMethod"})
    public UserController(UserService userService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @SuppressWarnings({"checkstyle:FinalParameters", "checkstyle:MissingJavadocMethod", "checkstyle:DesignForExtension"})
    @PostMapping(ApiPathConstants.ADD_NEW_USER)
    public User addUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:FinalParameters", "checkstyle:MissingJavadocMethod", "checkstyle:DesignForExtension"})
    @PostMapping(ApiPathConstants.GENERATE_TOKEN)
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired");
        }
    }
}
