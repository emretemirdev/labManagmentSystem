package com.emretemir.laboratorymanagementsystem.repository;

import com.emretemir.laboratorymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);

}
