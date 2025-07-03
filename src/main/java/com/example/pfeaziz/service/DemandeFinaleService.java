/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.service;


import com.example.pfeaziz.model.DemandeFinale;
import com.example.pfeaziz.repository.DemandeFinaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DemandeFinaleService {
    @Autowired
    private DemandeFinaleRepository demandeFinaleRepository;

    public List<DemandeFinale> getAllDemandesFinale() {
        return demandeFinaleRepository.findAll();
    }

    public DemandeFinale getDemandeFinaleById(Long id) {
        return demandeFinaleRepository.findById(id).orElse(null);
    }

    public DemandeFinale createDemandeFinale(DemandeFinale demandeFinale) {
        return demandeFinaleRepository.save(demandeFinale);
    }

    public DemandeFinale updateDemandeFinale(Long id, DemandeFinale demandeFinale) {
        demandeFinale.setId(id);
        return demandeFinaleRepository.save(demandeFinale);
    }

    public void deleteDemandeFinale(Long id) {
        demandeFinaleRepository.deleteById(id);
    }

    public DemandeFinale saveDemandeFinale(DemandeFinale demandeFinale) {
        return demandeFinaleRepository.save(demandeFinale);
    }
        public List<DemandeFinale> createBatchDemandesFinale(List<DemandeFinale> demandesFinale) {
        return demandeFinaleRepository.saveAll(demandesFinale);
    }
    
}
