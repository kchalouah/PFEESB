package com.example.pfeaziz.service;

import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.DemandeFinale;
import com.example.pfeaziz.model.Ilot;
import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.DemandeFinaleRepository;
import com.example.pfeaziz.repository.IlotRepository;
import com.example.pfeaziz.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandeFinaleService {

    @Autowired
    private DemandeFinaleRepository demandeFinaleRepository;

    @Autowired
    private App_userRepository appUserRepository;

    @Autowired
    private IlotRepository ilotRepository;

    @Autowired
    private MachineRepository machineRepository;

    public List<DemandeFinale> getAllDemandesFinale() {
        return demandeFinaleRepository.findAll();
    }

    public DemandeFinale getDemandeFinaleById(Long id) {
        return demandeFinaleRepository.findById(id).orElse(null);
    }

    public DemandeFinale createDemandeFinale(DemandeFinale demandeFinale) {
        resolveEntities(demandeFinale);
        return demandeFinaleRepository.save(demandeFinale);
    }

    public DemandeFinale updateDemandeFinale(Long id, DemandeFinale demandeFinale) {
        demandeFinale.setId(id);
        resolveEntities(demandeFinale);
        return demandeFinaleRepository.save(demandeFinale);
    }

    public void deleteDemandeFinale(Long id) {
        demandeFinaleRepository.deleteById(id);
    }

    public List<DemandeFinale> createBatchDemandesFinale(List<DemandeFinale> demandesFinale) {
        for (DemandeFinale demande : demandesFinale) {
            resolveEntities(demande);
        }
        return demandeFinaleRepository.saveAll(demandesFinale);
    }

    public DemandeFinale setFinalDecision(Long id, String decision, Long managerId) {
        DemandeFinale demande = demandeFinaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DemandeFinale not found with ID " + id));

        demande.setFinalDecision(decision.toUpperCase());
        demande.setApprovedDate(LocalDate.now().toString());

        if (managerId != null) {
            App_user manager = appUserRepository.findById(managerId)
                    .orElseThrow(() -> new RuntimeException("Manager not found with ID " + managerId));
            demande.setManager(manager);
        }

        return demandeFinaleRepository.save(demande);
    }

    private void resolveEntities(DemandeFinale demande) {
        if (demande.getOperateur() != null && demande.getOperateur().getId() != null) {
            App_user operateur = appUserRepository.findById(demande.getOperateur().getId())
                    .orElseThrow(() -> new RuntimeException("Operateur not found"));
            demande.setOperateur(operateur);
        }

        if (demande.getControleur() != null && demande.getControleur().getId() != null) {
            App_user controleur = appUserRepository.findById(demande.getControleur().getId())
                    .orElseThrow(() -> new RuntimeException("Controleur not found"));
            demande.setControleur(controleur);
        }

        if (demande.getManager() != null && demande.getManager().getId() != null) {
            App_user manager = appUserRepository.findById(demande.getManager().getId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            demande.setManager(manager);
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
