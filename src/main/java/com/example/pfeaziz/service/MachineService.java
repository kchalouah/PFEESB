package com.example.pfeaziz.service;


import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
    private final MachineRepository MachineRepository;

    public MachineService(MachineRepository MachineRepository) {
        this.MachineRepository = MachineRepository;
    }

    public List<Machine> getAllMachines() {
        return MachineRepository.findAll();
    }

    public Machine saveMachine(Machine Machine) {
        return MachineRepository.save(Machine);
    }

    public Machine updateMachine(Long id, Machine Machine) {
        if (MachineRepository.existsById(id)) {
            Machine.setId(id); // Set the ID for the update
            return MachineRepository.save(Machine);
        } else {
            return null; // Handle case where the Machine does not exist
        }
    }

    public boolean deleteMachine(Long id) {
        if (MachineRepository.existsById(id)) {
            MachineRepository.deleteById(id);
            return true; // Indicate successful deletion
        } else {
            return false; // Indicate that the Machine was not found
        }
    }
}
