package com.example.demo.service;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import java.util.List;

public interface CatalogService {
    ActiveIngredient addIngredient(ActiveIngredient ingredient);
    Medication addMedication(Medication medication);
    List<ActiveIngredient> getAllIngredients();
    List<Medication> getAllMedications();
}