/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.service;


import com.example.pfeaziz.model.DemandeTe;
import com.example.pfeaziz.repository.DemandeTeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeTeService {

    @Autowired
    private DemandeTeRepository demandeTeRepository;

    public List<DemandeTe> getAllDemandesTe() {
        return demandeTeRepository.findAll();
    }

    public DemandeTe getDemandeTeById(Long id) {
        return demandeTeRepository.findById(id).orElse(null);
    }

    public DemandeTe createDemandeTe(DemandeTe demande) {
        return demandeTeRepository.save(demande);
    }

    public DemandeTe updateDemandeTe(Long id, DemandeTe demandeTe) {
        demandeTe.setId(id);
        return demandeTeRepository.save(demandeTe);
    }

    public void deleteDemandeTe(Long id) {
        demandeTeRepository.deleteById(id);
    }

    public DemandeTe saveDemandeTe(DemandeTe demandeTe) {
        return demandeTeRepository.save(demandeTe);
    }
        public List<DemandeTe> createBatchDemandesTe(List<DemandeTe> demandesTe) {
        return demandeTeRepository.saveAll(demandesTe);
    }    
    
}
