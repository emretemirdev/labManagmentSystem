package com.emretemir.laboratorymanagementsystem.service;

import com.emretemir.laboratorymanagementsystem.dto.Auth.CreateUserRequest;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public ResponseEntity createUser(CreateUserRequest request) {
        User newUser = User.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .hospitalId(request.hospitalId())
                .authorities(request.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
                 userRepository.save(newUser);
               return ResponseEntity.ok("User oluşturuldu");
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}