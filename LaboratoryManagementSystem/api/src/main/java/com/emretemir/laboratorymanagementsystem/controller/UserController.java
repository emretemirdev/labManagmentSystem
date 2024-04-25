package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.dto.Auth.AuthRequest;
import com.emretemir.laboratorymanagementsystem.dto.Auth.CreateUserRequest;
import com.emretemir.laboratorymanagementsystem.dto.Auth.UserInfoDTO;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.service.JWTService;
import com.emretemir.laboratorymanagementsystem.service.UserService;
import com.emretemir.laboratorymanagementsystem.core.constants.ApiPathConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiPathConstants.USER_BASE_URL)
@Slf4j
public class UserController {

    private final UserService userService;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Secured("ROLE_USER")
    @GetMapping(ApiPathConstants.GET_USER_INFO)
    public UserInfoDTO getUserInfo(@PathVariable Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı Id:" + userId));

        UserInfoDTO userInfo = new UserInfoDTO(
                user.getId(),
                user.getName(),
                user.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .collect(Collectors.toList()),
                user.getHospitalId()
        );
        return userInfo;
    }

    @PostMapping(ApiPathConstants.ADD_NEW_USER)
    public ResponseEntity<String> addUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>() {{
                put("message", "Hatalı kullanıcı adı veya şifre");
            }});
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>() {{
                put("message", "Token süresi dolmuş. Lütfen tekrar giriş yapın.");
            }});
        }
    }
}
