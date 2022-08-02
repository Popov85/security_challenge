package com.popov.security_challenge.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="credentials")
public class Credentials {
    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Override
    public String toString() {
        return "Credentials{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

