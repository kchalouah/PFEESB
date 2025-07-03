package com.example.pfeaziz.controller;


import com.example.pfeaziz.model.Programme;
import com.example.pfeaziz.service.ProgrammeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/programmes")
public class ProgrammeController {

    private final ProgrammeService programmeService;

    public ProgrammeController(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    @GetMapping
    public List<Programme> getProgrammes() {
        return programmeService.getAllProgrammes();
    }

    @PostMapping
    public ResponseEntity<Programme> createProgramme(@RequestBody Programme programme) {
        Programme savedProgramme = programmeService.saveProgramme(programme);
        return ResponseEntity.ok(savedProgramme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Programme> updateProgramme(@PathVariable Long id, @RequestBody Programme programme) {
        Programme updatedProgramme = programmeService.updateProgramme(id, programme);
        if (updatedProgramme != null) {
            return ResponseEntity.ok(updatedProgramme);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramme(@PathVariable Long id) {
        if (programmeService.deleteProgramme(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}