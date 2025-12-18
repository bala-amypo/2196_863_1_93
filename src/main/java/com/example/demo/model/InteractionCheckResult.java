package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GenerateValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 100,message = "Name must be between 2 and 100 characters")
    @Column(nullable =  false)

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable =  false, unique= true)
    private String email;

    @Not
}