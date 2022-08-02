package com.popov.security_challenge.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
