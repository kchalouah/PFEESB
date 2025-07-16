package com.example.pfeaziz.service;


import com.example.pfeaziz.model.Ilot;
import com.example.pfeaziz.repository.IlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IlotService {

    private final IlotRepository ilotRepository;

    public IlotService(IlotRepository ilotRepository) {
        this.ilotRepository = ilotRepository;
    }

    public List<Ilot> getAllIlots() {
        if (ilotRepository == null) {
            System.err.println("IlotRepository is not injected!");
            return List.of();
        }
        return ilotRepository.findAll();
    }

    public Ilot saveIlot(Ilot ilot) {
        return ilotRepository.save(ilot);
    }

    public Ilot updateIlot(Long id, Ilot ilot) {
        if (ilotRepository.existsById(id)) {
            ilot.setId(id); // Set the ID for the update
            return ilotRepository.save(ilot);
        } else {
            return null; // Handle case where the Ilot does not exist
        }
    }

    public boolean deleteIlot(Long id) {
        if (ilotRepository.existsById(id)) {
            ilotRepository.deleteById(id);
            return true; // Indicate successful deletion
        } else {
            return false; // Indicate that the Ilot was not found
        }
    }
}
