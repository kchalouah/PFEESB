package com.example.pfeaziz.service;

import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.DemandeFinale;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.DemandeFinaleRepository;
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

    public List<DemandeFinale> createBatchDemandesFinale(List<DemandeFinale> demandesFinale) {
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
}
