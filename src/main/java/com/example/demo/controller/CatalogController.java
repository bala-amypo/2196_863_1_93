package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.model.InteractionCheckResult;
import com.example.demo.model.Medication;
import com.example.demo.service.CatalogService;
import com.example.demo.service.InteractionCheckResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@Tag(name = "Catalog", description = "Medication and ingredient catalog management")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private InteractionCheckResultService interactionService;

    

    @GetMapping("/medications")
    @Operation(summary = "Get all medications", description = "Retrieves a list of all medications")
    public ResponseEntity<List<Medication>> getAllMedications() {
        return ResponseEntity.ok(catalogService.getAllMedications());
    }

    @GetMapping("/medications/{id}")
    @Operation(summary = "Get medication by ID", description = "Retrieves a specific medication by its ID")
    public ResponseEntity<Medication> getMedication(@PathVariable Long id) {
        Medication medication = catalogService.getMedicationById(id);
        return ResponseEntity.ok(medication);
    }

    @PostMapping("/medications")
    @Operation(summary = "Add new medication", description = "Creates a new medication")
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication) {
        Medication savedMedication = catalogService.addMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMedication);
    }

    @PostMapping("/medications/check-interactions")
    @Operation(summary = "Check drug interactions", description = "Checks for interactions between multiple medications")
    public ResponseEntity<InteractionCheckResult> checkInteractions(@RequestBody List<Long> medicationIds) {
        InteractionCheckResult result = interactionService.checkInteractions(medicationIds);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/ingredients")
    @Operation(summary = "Get all active ingredients", description = "Retrieves a list of all active ingredients")
    public ResponseEntity<List<ActiveIngredient>> getAllIngredients() {
        return ResponseEntity.ok(catalogService.getAllIngredients());
    }

    @GetMapping("/ingredients/{id}")
    @Operation(summary = "Get ingredient by ID", description = "Retrieves a specific active ingredient by its ID")
    public ResponseEntity<ActiveIngredient> getIngredient(@PathVariable Long id) {
        ActiveIngredient ingredient = catalogService.getIngredientById(id);
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping("/ingredients")
    @Operation(summary = "Add new ingredient", description = "Creates a new active ingredient")
    public ResponseEntity<ActiveIngredient> addIngredient(@RequestBody ActiveIngredient ingredient) {
        ActiveIngredient savedIngredient = catalogService.addIngredient(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient);
    }

    @PutMapping("/ingredients/{id}")
    @Operation(summary = "Update ingredient", description = "Updates an existing active ingredient")
    public ResponseEntity<ActiveIngredient> updateIngredient(@PathVariable Long id, @RequestBody ActiveIngredient ingredient) {
        ActiveIngredient updatedIngredient = catalogService.updateIngredient(id, ingredient);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/ingredients/{id}")
    @Operation(summary = "Delete ingredient", description = "Deletes an active ingredient")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        catalogService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}