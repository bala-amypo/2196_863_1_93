package com.example.demo.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "active_ingredients")
public class ActiveIngredient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false, unique= true)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Medication> medications = new HashSet<>();

    public ActiveIngredient(){

    }
    public ActiveIngredient(String name){
        this.name = name;
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

    private Set<Medication> getMedications(){
        return medications
    }

    public void setMedications(Set<Medication> medications){
        this.medications = medications;
    }
    

}