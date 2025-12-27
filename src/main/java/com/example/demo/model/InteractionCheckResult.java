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

    @Column(name = "has_interactions", nullable = false)
    private boolean hasInteractions = false;   // ⭐ IMPORTANT

    @Column(name = "checked_at", nullable = false)
    private LocalDateTime checkedAt = LocalDateTime.now();

    /* ================= Constructors ================= */

    public InteractionCheckResult() {
        this.checkedAt = LocalDateTime.now();
        this.hasInteractions = false;
    }

    public InteractionCheckResult(String medications, String interactions) {
        this.medications = medications;
        this.interactions = interactions;
        this.hasInteractions =
                interactions != null && !interactions.isBlank();
        this.checkedAt = LocalDateTime.now();
    }

    /* ================= Getters & Setters ================= */

    public Long getId() {
        return id;
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
        this.hasInteractions =
                interactions != null && !interactions.isBlank();
    }

    public boolean isHasInteractions() {
        return hasInteractions;
    }

    public void setHasInteractions(boolean hasInteractions) {
        this.hasInteractions = hasInteractions;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }
}
