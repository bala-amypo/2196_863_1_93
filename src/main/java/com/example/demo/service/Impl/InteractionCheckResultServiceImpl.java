package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.InteractionRule;
import com.example.demo.model.Medication;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionCheckResultService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InteractionCheckResultServiceImpl implements InteractionCheckResultService {

    private final InteractionCheckResultRepository repository;
    private final MedicationRepository medicationRepository;
    private final InteractionRuleRepository interactionRuleRepository;

    public InteractionCheckResultServiceImpl(InteractionCheckResultRepository repository, 
                                           MedicationRepository medicationRepository,
                                           InteractionRuleRepository interactionRuleRepository) {
        this.repository = repository;
        this.medicationRepository = medicationRepository;
        this.interactionRuleRepository = interactionRuleRepository;
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
        existing.setMedicationIds(result.getMedicationIds());
        existing.setHasInteractions(result.isHasInteractions());
        existing.setResultSummary(result.getResultSummary());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        if (medicationIds == null) {
            throw new IllegalArgumentException("Medication IDs cannot be null");
        }
        
        if (medicationIds.isEmpty()) {
            InteractionCheckResult result = new InteractionCheckResult();
            result.setMedicationIds(medicationIds);
            result.setHasInteractions(false);
            result.setResultSummary("No medications provided");
            return repository.save(result);
        }
        
        List<Medication> medications = medicationRepository.findAllById(medicationIds);
        
        if (medications.size() != medicationIds.size()) {
            throw new ResourceNotFoundException("One or more medications not found");
        }

        StringBuilder interactionResults = new StringBuilder();
        boolean foundInteractions = false;

        for (int i = 0; i < medications.size(); i++) {
            for (int j = i + 1; j < medications.size(); j++) {
                Medication med1 = medications.get(i);
                Medication med2 = medications.get(j);
                
                if (med1.getIngredients() != null && med2.getIngredients() != null) {
                    for (ActiveIngredient ing1 : med1.getIngredients()) {
                        for (ActiveIngredient ing2 : med2.getIngredients()) {
                            Optional<InteractionRule> rule = interactionRuleRepository
                                    .findRuleBetweenIngredients(ing1.getId(), ing2.getId());
                            
                            if (rule.isPresent()) {
                                if (foundInteractions) {
                                    interactionResults.append("; ");
                                }
                                interactionResults.append(rule.get().getSeverity())
                                        .append(": ")
                                        .append(rule.get().getDescription());
                                foundInteractions = true;
                            }
                        }
                    }
                }
            }
        }

        if (!foundInteractions) {
            interactionResults.append("No interactions found");
        }

        InteractionCheckResult result = new InteractionCheckResult();
        result.setMedicationIds(medicationIds);
        result.setHasInteractions(foundInteractions);
        result.setResultSummary(interactionResults.toString());
        return repository.save(result);
    }

    @Override
    public List<InteractionCheckResult> getCheckHistory() {
        return repository.findAllByOrderByCheckDateDesc();
    }
}