package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Medication;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public Medication findById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found"));
    }

    @Override
    public Medication getMedicationById(Long id) {
        return findById(id);
    }

    @Override
    public Medication createMedication(String name) {
        Medication medication = new Medication(name);
        return medicationRepository.save(medication);
    }

    @Override
    public List<Medication> searchMedications(String name) {
        return medicationRepository.findByNameContainingIgnoreCase(name);
    }
}