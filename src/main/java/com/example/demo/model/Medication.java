package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "medications")
public class Medication{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Medication name is required")
    @Size(min = 2, max = 100, message = "Medication name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "medication_ingredients",joinColumns = @JoinColumn(name = "medication_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @JsonIgnore
    private Set<ActiveIngredient> ingredients = new HashSet<>();

    public Medication(){

    }
    public Medication(String name){
        this.name = name;
        this.ingredients = new HashSet<>();
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Set<ActiveIngredient> getIngredients(){
        return ingredients;
    }
    public void setIngredients(Set<ActiveIngredient> ingredients){
        this.ingredients = ingredients;
    }
    public void addIngredient(ActiveIngredient ingredient){
        this.ingredients.add(ingredient);
    }
    public void removeIngredient(ActiveIngredient ingredient){
        this.ingredients.remove(ingredient);
    }

}