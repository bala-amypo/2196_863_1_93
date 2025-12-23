package com.example.demo.service;

import com.example.demo.model.InteractionRule;
import java.util.List;

public interface InteractionRuleService {
    InteractionRule save(InteractionRule rule);
    InteractionRule findById(Long id);
    List<InteractionRule> findAll();
    InteractionRule update(Long id, InteractionRule rule);
    void deleteById(Long id);
}