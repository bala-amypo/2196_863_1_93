package com.example.demo.controller;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.Medication;
import com.example.demo.service.InteractionCheckResultService;
import com.example.demo.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
@Tag(name = "Medications", description = "Medication management and interaction checking")
public class MedicationController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private InteractionCheckResultService interactionService;

    @GetMapping
    @Operation(summary = "Get all medications", description = "Retrieves a list of all medications")
    public ResponseEntity<List<Medication>> getAllMedications() {
        return ResponseEntity.ok(catalogService.getAllMedications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medication by ID", description = "Retrieves a specific medication by its ID")
    public ResponseEntity<Medication> getMedication(@PathVariable Long id) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/check-interactions")
    @Operation(summary = "Check drug interactions", description = "Checks for interactions between multiple medications")
    public ResponseEntity<InteractionCheckResult> checkInteractions(@RequestBody List<Long> medicationIds) {
        InteractionCheckResult result = interactionService.checkInteractions(medicationIds);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/interaction-history")
    @Operation(summary = "Get interaction history", description = "Retrieves the history of interaction checks")
    public ResponseEntity<List<InteractionCheckResult>> getInteractionHistory() {
        return ResponseEntity.ok(interactionService.getCheckHistory());
    }
}