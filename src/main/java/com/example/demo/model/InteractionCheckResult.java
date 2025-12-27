package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interaction_check_results")
public class InteractionCheckResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medications;

    @Column(columnDefinition = "TEXT")
    private String interactions;

    @Column(nullable = false)
    private LocalDateTime checkedAt;

    @Column(name = "has_interactions", nullable = false)
    private Boolean hasInteractions = false; // Default to false

    /* ================= Constructors ================= */

    public InteractionCheckResult() {
        this.checkedAt = LocalDateTime.now();
        this.hasInteractions = false;
    }

    public InteractionCheckResult(String medications, String interactions) {
        this.medications = medications;
        this.interactions = interactions;
        this.checkedAt = LocalDateTime.now();
        this.hasInteractions = determineHasInteractions(interactions);
    }

    /* ================= Getters and Setters ================= */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public Boolean getHasInteractions() {
        return hasInteractions;
    }

    public void setHasInteractions(Boolean hasInteractions) {
        this.hasInteractions = hasInteractions;
    }

    /**
     * Helper method to determine if interactions exist based on the JSON content
     */
    private Boolean determineHasInteractions(String interactions) {
        if (interactions == null || interactions.trim().isEmpty()) {
            return false;
        }
        // Simple check: if interactions JSON contains actual interaction data
        // You can make this more sophisticated based on your JSON structure
        return interactions.contains("\"totalInteractions\": 0") ? false : 
               interactions.contains("\"interactions\": []") ? false : true;
    }
}