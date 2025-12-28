package com.example.demo.service.impl;

import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
    
    @Autowired
    private InteractionRuleRepository ruleRepository;
    
    // ADD THIS NO-ARG CONSTRUCTOR
    public RuleServiceImpl() {
    }
    
    public RuleServiceImpl(InteractionRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }
    
    @Override
    public InteractionRule addRule(InteractionRule rule) {
        return ruleRepository.save(rule);
    }
    
    @Override
    public List<InteractionRule> getAllRules() {
        return ruleRepository.findAll();
    }
    
    @Override
    public InteractionRule findById(Long id) {
        return ruleRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<InteractionRule> findInteractionsBetweenIngredients(List<Long> ingredientIds) {
        List<InteractionRule> allInteractions = new java.util.ArrayList<>();
        
        // Check all pairs of ingredients
        for (int i = 0; i < ingredientIds.size(); i++) {
            for (int j = i + 1; j < ingredientIds.size(); j++) {
                Long id1 = ingredientIds.get(i);
                Long id2 = ingredientIds.get(j);
                
                // Check both directions (A,B) and (B,A)
                java.util.Optional<InteractionRule> rule = ruleRepository.findRuleBetweenIngredients(id1, id2);
                if (rule.isPresent()) {
                    allInteractions.add(rule.get());
                }
            }
        }
        
        return allInteractions;
    }
}