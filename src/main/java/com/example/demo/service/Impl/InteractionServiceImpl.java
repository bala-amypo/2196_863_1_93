package com.example.demo.service.impl;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionCheckResultService;
import com.example.demo.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {
    
    @Autowired
    private InteractionCheckResultService interactionCheckResultService;
    
    // ADD THIS NO-ARG CONSTRUCTOR
    public InteractionServiceImpl() {
    }
    
    public InteractionServiceImpl(InteractionCheckResultService interactionCheckResultService) {
        this.interactionCheckResultService = interactionCheckResultService;
    }
    
    @Override
    public InteractionCheckResult checkInteractions(List<Long> medicationIds) {
        return interactionCheckResultService.checkInteractions(medicationIds);
    }
    
    @Override
    public InteractionCheckResult getResult(Long id) {
        return interactionCheckResultService.findById(id);
    }
}