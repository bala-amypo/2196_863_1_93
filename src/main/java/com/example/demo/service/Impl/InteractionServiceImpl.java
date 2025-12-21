package com.example.demo.service.impl;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.InteractionRule;
import com.example.demo.model.Medication;
import com.example.demo.repository.InteractionCheckResultRepository;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private InteractionRuleRepository interactionRuleRepository;

    @Autowired
    private InteractionCheckResultRepository resultRepository;

    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        List<Medication> medications = medicationRepository.findAllById(medicationIds);
        String medicationNames = medications.stream()
                .map(Medication::getName)
                .collect(Collectors.joining(", "));

        StringBuilder interactionResults = new StringBuilder();
        boolean foundInteractions = false;

        // Check interactions between all pairs of medications
        for (int i = 0; i < medications.size(); i++) {
            for (int j = i + 1; j < medications.size(); j++) {
                Medication med1 = medications.get(i);
                Medication med2 = medications.get(j);
                
                // Check interactions between all ingredient pairs
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

        if (!foundInteractions) {
            interactionResults.append("No interactions found");
        }

        InteractionCheckResult result = new InteractionCheckResult(medicationNames, interactionResults.toString());
        return resultRepository.save(result);
    }

    @Override
    public List<InteractionCheckResult> getCheckHistory() {
        return resultRepository.findAllByOrderByCheckedAtDesc();
    }
}