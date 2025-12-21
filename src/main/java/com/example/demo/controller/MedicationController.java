package com.example.demo.controller;

import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.Medication;
import com.example.demo.service.InteractionService;
import com.example.demo.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private InteractionService interactionService;

    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedication(@PathVariable Long id) {
        return ResponseEntity.ok(medicationService.findById(id));
    }

    @PostMapping("/check-interactions")
    public ResponseEntity<InteractionCheckResult> checkInteractions(@RequestBody List<Long> medicationIds) {
        InteractionCheckResult result = interactionService.checkInteractions(medicationIds);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/interaction-history")
    public ResponseEntity<List<InteractionCheckResult>> getInteractionHistory() {
        return ResponseEntity.ok(interactionService.getCheckHistory());
    }
}