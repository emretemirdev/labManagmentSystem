package com.emretemir.laboratorymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;


    @NotNull
    @Column(unique = true)
    private Long hospitalId;


    private boolean accountNonExpired;
    private boolean isEnabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<Report> reports;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;
}