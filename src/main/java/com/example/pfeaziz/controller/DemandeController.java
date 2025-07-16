package com.example.pfeaziz.controller;

import com.example.pfeaziz.model.Demande;
import com.example.pfeaziz.service.DemandeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    // 🟢 Get all
    @GetMapping
    public List<Demande> getAllDemandes() {
        return demandeService.getAllDemandes();
    }

    // 🟡 Get by ID
    @GetMapping("/{id}")
    public Demande getDemandeById(@PathVariable Long id) {
        return demandeService.getDemandeById(id);
    }

    // 🟢 Create one
    @PostMapping
    public Demande createDemande(@RequestBody Demande demande) {
        return demandeService.createDemande(demande);
    }

    // 🟠 Update
    @PutMapping("/{id}")
    public Demande updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        return demandeService.updateDemande(id, demande);
    }

    // 🔴 Delete
    @DeleteMapping("/{id}")
    public void deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
    }

    // 📥 Batch creation
    @PostMapping("/batch")
    public List<Demande> createBatchDemandes(@RequestBody List<Demande> demandes) {
        return demandeService.createBatchDemandes(demandes);
    }

    // 📤 Export to Excel
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesToExcel() throws IOException {
        List<Demande> demandes = demandeService.getAllDemandes();
        if (demandes == null) demandes = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Demandes");

            // Header
            Row header = sheet.createRow(0);
            String[] columns = {
                    "ID", "OF", "Date Demande", "Status", "Durée (min)", "ETQ",
                    "Started", "Finished", "Nb Produit Contrôlé",
                    "Ilot", "Machine", "Opérateur", "Contrôleur"
            };
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            // Data rows
            int rowNum = 1;
            for (Demande d : demandes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getId() != null ? d.getId() : 0);
                row.createCell(1).setCellValue(nullToNA(d.getOf_demande()));
                row.createCell(2).setCellValue(nullToNA(d.getDate_demande()));
                row.createCell(3).setCellValue(nullToNA(d.getStatus()));
                row.createCell(4).setCellValue(d.getDuree_en_minutes() != null ? d.getDuree_en_minutes() : 0);
                row.createCell(5).setCellValue(nullToNA(d.getEtq()));
                row.createCell(6).setCellValue(d.getStarted() != null && d.getStarted());
                row.createCell(7).setCellValue(d.getFinished() != null && d.getFinished());
                row.createCell(8).setCellValue(d.getNombre_produit_controle() != null ? d.getNombre_produit_controle() : 0);
                row.createCell(9).setCellValue(d.getIlot() != null ? d.getIlot().getName() : "N/A");
                row.createCell(10).setCellValue(d.getMachine() != null ? d.getMachine().getName() : "N/A");
                row.createCell(11).setCellValue(d.getOperateur() != null ? d.getOperateur().getUsername() : "N/A");
                row.createCell(12).setCellValue(d.getControleur() != null ? d.getControleur().getUsername() : "N/A");
            }

            // Output stream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=demandes.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
        }
    }

    // Utility method to convert nulls to "N/A"
    private String nullToNA(String value) {
        return (value != null && !value.isBlank()) ? value : "N/A";
    }
}
