package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.Medication;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionCheckResultService;
import com.example.demo.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InteractionCheckResultServiceImpl implements InteractionCheckResultService {

    private final InteractionCheckResultRepository repository;
    private final MedicationRepository medicationRepository;
    private final RuleService ruleService;

    public InteractionCheckResultServiceImpl(InteractionCheckResultRepository repository,
                                           MedicationRepository medicationRepository,
                                           RuleService ruleService) {
        this.repository = repository;
        this.medicationRepository = medicationRepository;
        this.ruleService = ruleService;
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

        // Get all medications and extract ingredients
        List<Long> ingredientIds = extractIngredientIds(medicationIds);
        
        // Find interaction rules between ingredients
        List<InteractionRule> foundInteractions = ruleService.findInteractionsBetweenIngredients(ingredientIds);
        
        // Convert medication IDs to a comma-separated string
        String medications = medicationIds.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        // Build interactions JSON
        String interactions = buildInteractionsJson(foundInteractions);

        InteractionCheckResult result = new InteractionCheckResult(medications, interactions);
        // The constructor will automatically set hasInteractions based on found interactions

        return repository.save(result);
    }
    
    private List<Long> extractIngredientIds(List<Long> medicationIds) {
        return medicationIds.stream()
                .map(id -> medicationRepository.findById(id).orElse(null))
                .filter(medication -> medication != null)
                .flatMap(medication -> medication.getIngredients().stream())
                .map(ActiveIngredient::getId)
                .distinct()
                .collect(Collectors.toList());
    }
    
    private String buildInteractionsJson(List<InteractionRule> interactions) {
        if (interactions.isEmpty()) {
            return "{\"totalInteractions\": 0, \"interactions\": []}";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("{\"totalInteractions\": ").append(interactions.size()).append(", \"interactions\": [");
        
        for (int i = 0; i < interactions.size(); i++) {
            InteractionRule rule = interactions.get(i);
            json.append("{");
            json.append("\"ingredientA\": \"").append(rule.getIngredientA().getName()).append("\",");
            json.append("\"ingredientB\": \"").append(rule.getIngredientB().getName()).append("\",");
            json.append("\"severity\": \"").append(rule.getSeverity()).append("\",");
            json.append("\"description\": \"").append(rule.getDescription()).append("\"");
            json.append("}");
            
            if (i < interactions.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]}");
        return json.toString();
    }

    @Override
    public List<InteractionCheckResult> getCheckHistory() {
        return repository.findAllByOrderByCheckedAtDesc();
    }
}