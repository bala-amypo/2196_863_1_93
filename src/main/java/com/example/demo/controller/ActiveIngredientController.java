package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/active-ingredients")
public class ActiveIngredientController {

    private final CatalogService catalogService;

    public ActiveIngredientController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping
    public ResponseEntity<ActiveIngredient> create(@RequestBody ActiveIngredient ingredient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.addIngredient(ingredient));
    }

    @GetMapping
    public ResponseEntity<List<ActiveIngredient>> getAll() {
        return ResponseEntity.ok(catalogService.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiveIngredient> getById(@PathVariable Long id) {
        ActiveIngredient ingredient = catalogService.getIngredientById(id);
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActiveIngredient> update(@PathVariable Long id, @RequestBody ActiveIngredient ingredient) {
        ActiveIngredient updated = catalogService.updateIngredient(id, ingredient);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        catalogService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}