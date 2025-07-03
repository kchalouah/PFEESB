package com.example.pfeaziz.service;


import com.example.pfeaziz.model.Programme;
import com.example.pfeaziz.repository.ProgrammeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProgrammeService {

    private final ProgrammeRepository programmeRepository;

    public ProgrammeService(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }

    public List<Programme> getAllProgrammes() {
        return programmeRepository.findAll();
    }

    public Programme saveProgramme(Programme programme) {
        return programmeRepository.save(programme);
    }

    public Programme updateProgramme(Long id, Programme programme) {
        if (programmeRepository.existsById(id)) {
            programme.setId(id); // Ensure the ID is set for updating
            return programmeRepository.save(programme);
        } else {
            return null; // or throw an exception if preferred
        }
    }

    public boolean deleteProgramme(Long id) {
        if (programmeRepository.existsById(id)) {
            programmeRepository.deleteById(id);
            return true;
        } else {
            return false; // or throw an exception if preferred
        }
    }
}