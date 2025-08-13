package com.example.pfeaziz.service;

import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.Demande;
import com.example.pfeaziz.model.Ilot;
import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.DemandeRepository;
import com.example.pfeaziz.repository.IlotRepository;
import com.example.pfeaziz.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {

    @Autowired private DemandeRepository demandeRepository;
    @Autowired private App_userRepository userRepository;
    @Autowired private IlotRepository ilotRepository;
    @Autowired private MachineRepository machineRepository;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Demande getDemandeById(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }

    public Demande createDemande(Demande demande) {
        resolveEntities(demande);
        return demandeRepository.save(demande);
    }

    public Demande updateDemande(Long id, Demande demande) {
        demande.setId(id);
        resolveEntities(demande);
        return demandeRepository.save(demande);
    }

    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    public Demande saveDemande(Demande demande) {
        resolveEntities(demande);
        return demandeRepository.save(demande);
    }

    public List<Demande> createBatchDemandes(List<Demande> demandes) {
        for (Demande demande : demandes) {
            resolveEntities(demande);
        }
        return demandeRepository.saveAll(demandes);
    }

    // ✅ Resolve foreign key relations using repositories
    private void resolveEntities(Demande demande) {
        if (demande.getOperateur() != null && demande.getOperateur().getId() != null) {
            App_user operateur = userRepository.findById(demande.getOperateur().getId())
                    .orElseThrow(() -> new RuntimeException("Operateur not found"));
            demande.setOperateur(operateur);
        }

        if (demande.getControleur() != null && demande.getControleur().getId() != null) {
            App_user controleur = userRepository.findById(demande.getControleur().getId())
                    .orElseThrow(() -> new RuntimeException("Controleur not found"));
            demande.setControleur(controleur);
        }

        if (demande.getIlot() != null && demande.getIlot().getId() != null) {
            Ilot ilot = ilotRepository.findById(demande.getIlot().getId())
                    .orElseThrow(() -> new RuntimeException("Ilot not found"));
            demande.setIlot(ilot);
        }

        if (demande.getMachine() != null && demande.getMachine().getId() != null) {
            Machine machine = machineRepository.findById(demande.getMachine().getId())
                    .orElseThrow(() -> new RuntimeException("Machine not found"));
            demande.setMachine(machine);
        }
    }
}
