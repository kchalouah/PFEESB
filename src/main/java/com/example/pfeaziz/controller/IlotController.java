package com.example.pfeaziz.controller;


import com.example.pfeaziz.model.Ilot;
import com.example.pfeaziz.service.IlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ilots")
public class IlotController {

    private final IlotService ilotService;

    public IlotController(IlotService ilotService) {
        this.ilotService = ilotService;
    }

    @GetMapping
    public List<Ilot> getIlots() {
        return ilotService.getAllIlots();
    }

    @PostMapping
    public ResponseEntity<Ilot> createIlot(@RequestBody Ilot ilot) {
        Ilot savedIlot = ilotService.saveIlot(ilot);
        return ResponseEntity.ok(savedIlot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ilot> updateIlot(@PathVariable Long id, @RequestBody Ilot ilot) {
        Ilot updatedIlot = ilotService.updateIlot(id, ilot);
        if (updatedIlot != null) {
            return ResponseEntity.ok(updatedIlot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIlot(@PathVariable Long id) {
        if (ilotService.deleteIlot(id)) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }
}

