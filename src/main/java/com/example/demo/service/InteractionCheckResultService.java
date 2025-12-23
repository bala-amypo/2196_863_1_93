package com.example.demo.service;

import com.example.demo.model.InteractionCheckResult;
import java.util.List;

public interface InteractionCheckResultService {
    InteractionCheckResult save(InteractionCheckResult result);
    InteractionCheckResult findById(Long id);
    List<InteractionCheckResult> findAll();
    InteractionCheckResult update(Long id, InteractionCheckResult result);
    void deleteById(Long id);
    InteractionCheckResult checkInteractions(List<Long> medicationIds);
    List<InteractionCheckResult> getCheckHistory();
}