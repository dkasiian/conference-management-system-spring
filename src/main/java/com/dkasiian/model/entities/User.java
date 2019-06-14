package com.dkasiian.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_login")
    @Length(min = 3, message = "*Your login must have at least 3 characters")
    @NotEmpty(message = "*Please provide your login")
    private String login;

    @Column(name = "user_password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "user_name")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @Column(name = "user_surname")
    @NotEmpty(message = "*Please provide your last name")
    private String surname;

    @Column(name = "user_email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "user_role")
//    @NotEmpty(message = "*Please provide your role")
    @NotNull(message = "*Please provide your role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
