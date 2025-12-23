package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InteractionRule;
import com.example.demo.repository.InteractionRuleRepository;
import com.example.demo.service.InteractionRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionRuleServiceImpl implements InteractionRuleService {

    private final InteractionRuleRepository interactionRuleRepository;

    public InteractionRuleServiceImpl(InteractionRuleRepository interactionRuleRepository) {
        this.interactionRuleRepository = interactionRuleRepository;
    }

    @Override
    public InteractionRule save(InteractionRule rule) {
        return interactionRuleRepository.save(rule);
    }

    @Override
    public InteractionRule findById(Long id) {
        return interactionRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InteractionRule not found"));
    }

    @Override
    public List<InteractionRule> findAll() {
        return interactionRuleRepository.findAll();
    }

    @Override
    public InteractionRule update(Long id, InteractionRule rule) {
        InteractionRule existing = findById(id);
        existing.setIngredientA(rule.getIngredientA());
        existing.setIngredientB(rule.getIngredientB());
        existing.setSeverity(rule.getSeverity());
        existing.setDescription(rule.getDescription());
        return interactionRuleRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!interactionRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("InteractionRule not found");
        }
        interactionRuleRepository.deleteById(id);
    }
}