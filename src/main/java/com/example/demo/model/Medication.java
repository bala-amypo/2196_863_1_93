package com.example.demo.model;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "medications")
public class Medication{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "medication_ingredients",joinColumns = @JoinColumn(name = "medication_id"), inverseJoinColumns = @JoinColumn(name = "ingredients_id")
    )
    @JsonIgnore
    private Set<ActiveIngredient> ingredients = new HashSet<>();

    public Medication(){

    }
    public Mediaction(String name){
        this.name = name;
        this.ingredients
    }
}

