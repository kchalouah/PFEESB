/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.service;


import java.util.List;

import com.example.pfeaziz.model.DemandeDelegue;
import com.example.pfeaziz.repository.DemandeDelegueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author xfour
 */
@Service
public class DemandeDelegueService {
    @Autowired
    private DemandeDelegueRepository demandeDelegueRepository;

    public List<DemandeDelegue> getAllDemandesDelegue() {
        return demandeDelegueRepository.findAll();
    }

    public DemandeDelegue getDemandeDelegueById(Long id) {
        return demandeDelegueRepository.findById(id).orElse(null);
    }

    public DemandeDelegue createDemandeDelegue(DemandeDelegue demandeDelegue) {
        return demandeDelegueRepository.save(demandeDelegue);
    }

    public DemandeDelegue updateDemandeDelegue(Long id, DemandeDelegue demandeDelegue) {
        demandeDelegue.setId(id);
        return demandeDelegueRepository.save(demandeDelegue);
    }

    public void deleteDemandeDelegue(Long id) {
        demandeDelegueRepository.deleteById(id);
    }

    public DemandeDelegue saveDemandeDelegue(DemandeDelegue demandeDelegue) {
        return demandeDelegueRepository.save(demandeDelegue);
    }
        public List<DemandeDelegue> createBatchDemandesDelegue(List<DemandeDelegue> demandesDelegue) {
        return demandeDelegueRepository.saveAll(demandesDelegue);
    }
   

}
    

