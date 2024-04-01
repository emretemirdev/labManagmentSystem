package com.emretemir.laboratorymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@SuppressWarnings("checkstyle:LineLength")
@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @SuppressWarnings("checkstyle:JavadocVariable")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private String name;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private String username;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private String password;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private String hospitalId;


    @SuppressWarnings("checkstyle:JavadocVariable")
    private boolean accountNonExpired;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private boolean isEnabled;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private boolean accountNonLocked;
    @SuppressWarnings("checkstyle:JavadocVariable")
    private boolean credentialsNonExpired;

    @SuppressWarnings({"checkstyle:WhitespaceAfter", "checkstyle:JavadocVariable"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<Report> reports;


    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:JavadocVariable"})
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;
}