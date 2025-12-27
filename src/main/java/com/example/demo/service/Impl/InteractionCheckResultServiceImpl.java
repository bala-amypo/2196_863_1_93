package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.service.InteractionCheckResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionCheckResultServiceImpl implements InteractionCheckResultService {

    private final InteractionCheckResultRepository repository;

    public InteractionCheckResultServiceImpl(InteractionCheckResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public InteractionCheckResult save(InteractionCheckResult result) {
        if (result == null) {
            throw new IllegalArgumentException("InteractionCheckResult cannot be null");
        }
        return repository.save(result);
    }

    @Override
    public InteractionCheckResult findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InteractionCheckResult not found"));
    }

    @Override
    public List<InteractionCheckResult> findAll() {
        return repository.findAll();
    }

    @Override
    public InteractionCheckResult update(Long id, InteractionCheckResult result) {
        if (result == null) {
            throw new IllegalArgumentException("InteractionCheckResult cannot be null");
        }

        InteractionCheckResult existing = findById(id);

        // Updated to match new field names
        existing.setMedications(result.getMedications());
        existing.setInteractions(result.getInteractions());

        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        if (medicationIds == null || medicationIds.isEmpty()) {
            throw new IllegalArgumentException("Medication IDs cannot be null or empty");
        }

        // Convert medication IDs to a comma-separated string
        String medications = medicationIds.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        // Create result with placeholder JSON
        String interactions = "{\"totalInteractions\": 0, \"interactions\": []}";

        InteractionCheckResult result = new InteractionCheckResult(medications, interactions);
        // The constructor will automatically set hasInteractions to false for this case

        return repository.save(result);
    }

    @Override
    public List<InteractionCheckResult> getCheckHistory() {
        return repository.findAllByOrderByCheckedAtDesc();
    }
}