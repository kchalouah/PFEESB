package com.example.pfeaziz.service;

import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.DemandeDelegue;
import com.example.pfeaziz.model.Ilot;
import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.DemandeDelegueRepository;
import com.example.pfeaziz.repository.IlotRepository;
import com.example.pfeaziz.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeDelegueService {

    @Autowired
    private DemandeDelegueRepository demandeDelegueRepository;

    @Autowired
    private App_userRepository appUserRepository;

    @Autowired
    private IlotRepository ilotRepository;

    @Autowired
    private MachineRepository machineRepository;

    public List<DemandeDelegue> getAllDemandesDelegue() {
        return demandeDelegueRepository.findAll();
    }

    public DemandeDelegue getDemandeDelegueById(Long id) {
        return demandeDelegueRepository.findById(id).orElse(null);
    }

    public DemandeDelegue createDemandeDelegue(DemandeDelegue demande) {
        resolveReferences(demande);
        return demandeDelegueRepository.save(demande);
    }

    public DemandeDelegue updateDemandeDelegue(Long id, DemandeDelegue demande) {
        demande.setId(id);
        resolveReferences(demande);
        return demandeDelegueRepository.save(demande);
    }

    public void deleteDemandeDelegue(Long id) {
        demandeDelegueRepository.deleteById(id);
    }

    public List<DemandeDelegue> createBatchDemandesDelegue(List<DemandeDelegue> demandes) {
        for (DemandeDelegue demande : demandes) {
            resolveReferences(demande);
        }
        return demandeDelegueRepository.saveAll(demandes);
    }

    private void resolveReferences(DemandeDelegue demande) {
        if (demande.getOperateur() != null && demande.getOperateur().getId() != null) {
            demande.setOperateur(appUserRepository.findById(demande.getOperateur().getId()).orElse(null));
        }
        if (demande.getControleur() != null && demande.getControleur().getId() != null) {
            demande.setControleur(appUserRepository.findById(demande.getControleur().getId()).orElse(null));
        }
        if (demande.getDelegatedTo() != null && demande.getDelegatedTo().getId() != null) {
            demande.setDelegatedTo(appUserRepository.findById(demande.getDelegatedTo().getId()).orElse(null));
        }
        if (demande.getIlot() != null && demande.getIlot().getId() != null) {
            demande.setIlot(ilotRepository.findById(demande.getIlot().getId()).orElse(null));
        }
        if (demande.getMachine() != null && demande.getMachine().getId() != null) {
            demande.setMachine(machineRepository.findById(demande.getMachine().getId()).orElse(null));
        }
    }
}
