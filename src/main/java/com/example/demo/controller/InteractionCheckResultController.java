package com.example.demo.controller;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.service.InteractionCheckResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
@Tag(name = "Drug Interactions", description = "Drug interaction check endpoints")
public class InteractionCheckResultController {

    private final InteractionCheckResultService service;

    public InteractionCheckResultController(InteractionCheckResultService service) {
        this.service = service;
    }

    @PostMapping("/check")
    @Operation(summary = "Check drug interactions", description = "Check for interactions between medications")
    public ResponseEntity<InteractionCheckResult> checkInteractions(@RequestBody List<Long> medicationIds) {
        InteractionCheckResult result = service.checkInteractions(medicationIds);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get interaction result by ID", description = "Retrieve a specific interaction check result")
    public ResponseEntity<InteractionCheckResult> getById(@PathVariable Long id) {
        InteractionCheckResult result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "Get all interaction results", description = "Retrieve all interaction check results")
    public ResponseEntity<List<InteractionCheckResult>> getAll() {
        List<InteractionCheckResult> results = service.findAll();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/history")
    @Operation(summary = "Get interaction check history", description = "Retrieve interaction check history ordered by date")
    public ResponseEntity<List<InteractionCheckResult>> getHistory() {
        List<InteractionCheckResult> history = service.getCheckHistory();
        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete interaction result", description = "Delete a specific interaction check result")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}