/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.controller;


import java.util.List;

import com.example.pfeaziz.model.Machine;
import com.example.pfeaziz.service.MachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xfour
 */

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public List<Machine> getMachines() {
        return machineService.getAllMachines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
        Machine machine = machineService.getMachineById(id);
        if (machine != null) {
            return ResponseEntity.ok(machine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Machine> createMachine(@RequestBody Machine machine) {
        Machine savedMachine = machineService.saveMachine(machine);
        return ResponseEntity.ok(savedMachine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Machine> updateMachine(@PathVariable Long id, @RequestBody Machine machine) {
        Machine updatedMachine = machineService.updateMachine(id, machine);
        if (updatedMachine != null) {
            return ResponseEntity.ok(updatedMachine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        if (machineService.deleteMachine(id)) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }

}
