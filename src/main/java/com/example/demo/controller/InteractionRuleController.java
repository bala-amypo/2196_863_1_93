package com.example.demo.controller;

import com.example.demo.model.InteractionRule;
import com.example.demo.service.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interaction-rules")
@Tag(name = "Interaction Rules", description = "Manage drug interaction rules")
public class InteractionRuleController {

    private final RuleService ruleService;

    public InteractionRuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    @Operation(summary = "Add interaction rule", description = "Creates a new drug interaction rule")
    public ResponseEntity<InteractionRule> addRule(@RequestBody InteractionRule rule) {
        InteractionRule savedRule = ruleService.addRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
    }

    @GetMapping
    @Operation(summary = "Get all interaction rules", description = "Retrieves all interaction rules")
    public ResponseEntity<List<InteractionRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get interaction rule by ID", description = "Retrieves a specific interaction rule")
    public ResponseEntity<InteractionRule> getRule(@PathVariable Long id) {
        InteractionRule rule = ruleService.findById(id);
        if (rule != null) {
            return ResponseEntity.ok(rule);
        }
        return ResponseEntity.notFound().build();
    }
}