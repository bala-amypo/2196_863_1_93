package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.repository.ActiveIngredientRepository;
import com.example.demo.service.ActiveIngredientService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActiveIngredientServiceImpl implements ActiveIngredientService {

    private final ActiveIngredientRepository repository;

    public ActiveIngredientServiceImpl(ActiveIngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActiveIngredient save(ActiveIngredient activeIngredient) {
        return repository.save(activeIngredient);
    }

    @Override
    public ActiveIngredient findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActiveIngredient not found"));
    }

    @Override
    public List<ActiveIngredient> findAll() {
        return repository.findAll();
    }

    @Override
    public ActiveIngredient update(Long id, ActiveIngredient activeIngredient) {
        ActiveIngredient existing = findById(id);
        existing.setName(activeIngredient.getName());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ActiveIngredient existing = findById(id);
        repository.delete(existing);
    }
}