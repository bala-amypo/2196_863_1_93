package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GenerateValue(strategy = GenerationType.IDENTITY)
    private Long id;
}