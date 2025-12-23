package com.example.demo.controller;

import com.example.demo.model.ActiveIngredient;
import com.example.demo.service.ActiveIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/active-ingredients")
public class ActiveIngredientController {

    private final ActiveIngredientService service;

    public ActiveIngredientController(ActiveIngredientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ActiveIngredient> create(@RequestBody ActiveIngredient ingredient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(ingredient));
    }

    @GetMapping
    public ResponseEntity<List<ActiveIngredient>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiveIngredient> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActiveIngredient> update(@PathVariable Long id, @RequestBody ActiveIngredient ingredient) {
        try {
            return ResponseEntity.ok(service.update(id, ingredient));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}