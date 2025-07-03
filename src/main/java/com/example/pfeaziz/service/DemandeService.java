/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.service;


import com.example.pfeaziz.model.Demande;
import com.example.pfeaziz.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Demande getDemandeById(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }

    public Demande createDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public Demande updateDemande(Long id, Demande demande) {
        demande.setId(id);
        return demandeRepository.save(demande);
    }

    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    public Demande saveDemande(Demande demande) {
        return demandeRepository.save(demande);
    }
        public List<Demande> createBatchDemandes(List<Demande> demandes) {
        return demandeRepository.saveAll(demandes);
    }
}


