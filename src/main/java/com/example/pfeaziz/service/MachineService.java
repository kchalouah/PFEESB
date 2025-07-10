package com.example.pfeaziz.service;


import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    public Machine getMachineById(Long id) {
        return machineRepository.findById(id).orElse(null);
    }

    public Machine saveMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    public Machine updateMachine(Long id, Machine machine) {
        if (machineRepository.existsById(id)) {
            machine.setId(id); // Set the ID for the update
            return machineRepository.save(machine);
        } else {
            return null; // Handle case where the machine does not exist
        }
    }

    public boolean deleteMachine(Long id) {
        if (machineRepository.existsById(id)) {
            machineRepository.deleteById(id);
            return true; // Indicate successful deletion
        } else {
            return false; // Indicate that the machine was not found
        }
    }
}
