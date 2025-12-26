package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    
    private final ActiveIngredientRepository ingredientRepository;
    private final MedicationRepository medicationRepository;
    
    public CatalogServiceImpl(ActiveIngredientRepository ingredientRepository, 
                             MedicationRepository medicationRepository) {
        this.ingredientRepository = ingredientRepository;
        this.medicationRepository = medicationRepository;
    }
    
    @Override
    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
    
    @Override
    public Medication addMedication(Medication medication) {
        return medicationRepository.save(medication);
    }
    
    @Override
    public List<ActiveIngredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
    
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
}