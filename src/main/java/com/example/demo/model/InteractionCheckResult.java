package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "interaction_check_results")
public class InteractionCheckResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
        name = "interaction_check_medications",
        joinColumns = @JoinColumn(name = "result_id")
    )
    @Column(name = "medication_id")
    private List<Long> medicationIds;

    private boolean hasInteractions;

    @Column(columnDefinition = "TEXT")
    private String resultSummary;

    private LocalDateTime checkDate;

    public InteractionCheckResult() {
        this.checkDate = LocalDateTime.now();
    }

    /* ===== Getters and Setters ===== */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMedicationIds() {
        return medicationIds;
    }

    public void setMedicationIds(List<Long> medicationIds) {
        this.medicationIds = medicationIds;
    }

    public boolean isHasInteractions() {
        return hasInteractions;
    }

    public void setHasInteractions(boolean hasInteractions) {
        this.hasInteractions = hasInteractions;
    }

    public String getResultSummary() {
        return resultSummary;
    }

    public void setResultSummary(String resultSummary) {
        this.resultSummary = resultSummary;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }
}
