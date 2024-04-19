package com.macromill.user.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "USERNAME")
    String username;

    @Column(name = "NICKNAME")
    String nickname;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "COMMENT")
    String comment;
}
