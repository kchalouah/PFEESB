package com.example.pfeaziz.service;

import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.DemandeTe;
import com.example.pfeaziz.model.Ilot;
import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.DemandeTeRepository;
import com.example.pfeaziz.repository.IlotRepository;
import com.example.pfeaziz.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeTeService {

    @Autowired
    private DemandeTeRepository demandeTeRepository;

    @Autowired
    private App_userRepository appUserRepository;

    @Autowired
    private IlotRepository ilotRepository;

    @Autowired
    private MachineRepository machineRepository;

    public List<DemandeTe> getAllDemandesTe() {
        return demandeTeRepository.findAll();
    }

    public DemandeTe getDemandeTeById(Long id) {
        return demandeTeRepository.findById(id).orElse(null);
    }

    public DemandeTe createDemandeTe(DemandeTe demande) {
        resolveReferences(demande);
        return demandeTeRepository.save(demande);
    }

    public DemandeTe updateDemandeTe(Long id, DemandeTe demande) {
        demande.setId(id);
        resolveReferences(demande);
        return demandeTeRepository.save(demande);
    }

    public void deleteDemandeTe(Long id) {
        demandeTeRepository.deleteById(id);
    }

    public List<DemandeTe> createBatchDemandesTe(List<DemandeTe> demandesTe) {
        for (DemandeTe demande : demandesTe) {
            resolveReferences(demande);
        }
        return demandeTeRepository.saveAll(demandesTe);
    }

    private void resolveReferences(DemandeTe demande) {
        if (demande.getOperateur() != null && demande.getOperateur().getId() != null) {
            demande.setOperateur(appUserRepository.findById(demande.getOperateur().getId()).orElse(null));
        }
        if (demande.getControleur() != null && demande.getControleur().getId() != null) {
            demande.setControleur(appUserRepository.findById(demande.getControleur().getId()).orElse(null));
        }
        if (demande.getIlot() != null && demande.getIlot().getId() != null) {
            demande.setIlot(ilotRepository.findById(demande.getIlot().getId()).orElse(null));
        }
        if (demande.getMachine() != null && demande.getMachine().getId() != null) {
            demande.setMachine(machineRepository.findById(demande.getMachine().getId()).orElse(null));
        }
    }
}
