package com.example.demo.service;

import com.example.demo.model.Medication;
import java.util.List;

public interface MedicationService {
    List<Medication> getAllMedications();
    Medication findById(Long id);
    Medication getMedicationById(Long id);
    Medication createMedication(String name);
    List<Medication> searchMedications(String name);
}