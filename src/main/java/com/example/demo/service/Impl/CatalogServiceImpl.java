package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.Medication;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    
    @Autowired
    private ActiveIngredientRepository ingredientRepository;
    
    @Autowired
    private MedicationRepository medicationRepository;
    
    // ADD THIS NO-ARG CONSTRUCTOR
    public CatalogServiceImpl() {
    }
    
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
    
    @Override
    public ActiveIngredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    }
    
    @Override
    public ActiveIngredient updateIngredient(Long id, ActiveIngredient ingredient) {
        ActiveIngredient existing = getIngredientById(id);
        existing.setName(ingredient.getName());
        return ingredientRepository.save(existing);
    }
    
    @Override
    public void deleteIngredient(Long id) {
        ActiveIngredient existing = getIngredientById(id);
        ingredientRepository.delete(existing);
    }
}