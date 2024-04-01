package com.emretemir.laboratorymanagementsystem.repository;

import com.emretemir.laboratorymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:GenericWhitespace"})
public interface UserRepository extends JpaRepository<User, Long>{
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    Optional<User> findByUsername(String username);


}
