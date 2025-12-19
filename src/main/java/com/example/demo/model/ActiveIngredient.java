package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class ActiveIngreddient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false, unique= true)
    private String name;

    public ActiveIngredient(){

    }
    public ActiveIngredient(String name){
        this.name = name;
        
    }

}