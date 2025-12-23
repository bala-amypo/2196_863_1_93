package com.example.demo.service;

import com.example.demo.model.ActiveIngredient;
import java.util.List;

public interface ActiveIngredientService {
    ActiveIngredient save(ActiveIngredient activeIngredient);
    ActiveIngredient findById(Long id);
    List<ActiveIngredient> findAll();
    ActiveIngredient update(Long id, ActiveIngredient activeIngredient);
    void deleteById(Long id);
}