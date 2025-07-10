/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.controller;


import java.util.List;

import com.example.pfeaziz.model.Metier;
import com.example.pfeaziz.service.MetierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xfour
 */
// MetierController.java
@RestController
@RequestMapping("/api/metiers")
public class MetierController {

    private final MetierService metierService;

    public MetierController(MetierService metierService) {
        this.metierService = metierService;
    }

    @GetMapping
    public List<Metier> getAllMetiers() {
        return metierService.getAllMetiers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metier> getMetierById(@PathVariable Long id) {
        Metier metier = metierService.getMetierById(id);
        if (metier != null) {
            return ResponseEntity.ok(metier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Metier addMetier(@RequestBody Metier metier) {
        return metierService.saveMetier(metier);
    }

    @PutMapping("/{id}")
    public Metier updateMetier(@PathVariable Long id, @RequestBody Metier metier) {
        return metierService.updateMetier(id, metier);
    }
        // Add the delete mapping method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetier(@PathVariable Long id) {
        metierService.deleteMetier(id); // Make sure this method exists in the service
        return ResponseEntity.noContent().build(); // Return a 204 No Content status
    }
}
