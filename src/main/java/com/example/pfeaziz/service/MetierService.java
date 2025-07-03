/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.service;


import java.util.List;

import com.example.pfeaziz.model.Metier;
import com.example.pfeaziz.repository.MetierRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author xfour
 */
// MetierService.java
@Service
public class MetierService {

    private final MetierRepository metierRepository;

    public MetierService(MetierRepository metierRepository) {
        this.metierRepository = metierRepository;
    }

    public List<Metier> getAllMetiers() {
        return metierRepository.findAll();
    }

    public Metier saveMetier(Metier metier) {
        return metierRepository.save(metier);
    }

    public Metier updateMetier(Long id, Metier metier) {
        // Fetch the existing Metier by id
        Metier existingMetier = metierRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Metier not found"));

        // Update fields
        existingMetier.setName(metier.getName());

        // Save the updated Metier back to the database
        return metierRepository.save(existingMetier);
    }
        public void deleteMetier(Long id) {
        // Check if the Metier exists before deleting
        if (!metierRepository.existsById(id)) {
            throw new IllegalArgumentException("Metier not found");
        }
        metierRepository.deleteById(id);
    }
}