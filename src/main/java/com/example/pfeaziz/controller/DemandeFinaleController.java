package com.example.pfeaziz.controller;

import com.example.pfeaziz.model.DemandeFinale;
import com.example.pfeaziz.service.DemandeFinaleService;
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
@RequestMapping("/api/demandes_finale")
public class DemandeFinaleController {

    @Autowired
    private DemandeFinaleService demandeFinaleService;

    // 🟢 Get all
    @GetMapping
    public List<DemandeFinale> getAllDemandesFinale() {
        return demandeFinaleService.getAllDemandesFinale();
    }

    // 🟡 Get by ID
    @GetMapping("/{id}")
    public DemandeFinale getDemandeFinaleById(@PathVariable Long id) {
        return demandeFinaleService.getDemandeFinaleById(id);
    }

    // 🟢 Create
    @PostMapping
    public DemandeFinale createDemandeFinale(@RequestBody DemandeFinale demandeFinale) {
        return demandeFinaleService.createDemandeFinale(demandeFinale);
    }

    // 🟠 Update
    @PutMapping("/{id}")
    public DemandeFinale updateDemandeFinale(@PathVariable Long id, @RequestBody DemandeFinale demandeFinale) {
        return demandeFinaleService.updateDemandeFinale(id, demandeFinale);
    }

    // 🔴 Delete
    @DeleteMapping("/{id}")
    public void deleteDemandeFinale(@PathVariable Long id) {
        demandeFinaleService.deleteDemandeFinale(id);
    }

    // 🆕 ✅ Approve / Reject a demande finale
    @PutMapping("/{id}/decision")
    public DemandeFinale setFinalDecision(
            @PathVariable Long id,
            @RequestParam String decision,
            @RequestParam(required = false) Long managerId) {
        return demandeFinaleService.setFinalDecision(id, decision, managerId);
    }

    // 📤 Export to Excel
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesFinaleToExcel() throws IOException {
        List<DemandeFinale> demandesFinale = demandeFinaleService.getAllDemandesFinale();
        if (demandesFinale == null) demandesFinale = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DemandesFinale");

            String[] headers = {
                    "ID", "Manager", "OF", "Date Demande", "Status", "Final Decision", "Approved Date",
                    "Ilot", "Machine", "Opérateur", "Contrôleur",
                    "Durée (min)", "ETQ", "Started", "Finished", "Nb Produits Contrôlés"
            };

            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (DemandeFinale d : demandesFinale) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getId() != null ? d.getId() : 0);
                row.createCell(1).setCellValue(d.getManager() != null ? d.getManager().getUsername() : "N/A");
                row.createCell(2).setCellValue(nullToNA(d.getOf_demande()));
                row.createCell(3).setCellValue(nullToNA(d.getDate_demande()));
                row.createCell(4).setCellValue(nullToNA(d.getStatus()));
                row.createCell(5).setCellValue(nullToNA(d.getFinalDecision()));
                row.createCell(6).setCellValue(nullToNA(d.getApprovedDate()));
                row.createCell(7).setCellValue(d.getIlot() != null ? d.getIlot().getName() : "N/A");
                row.createCell(8).setCellValue(d.getMachine() != null ? d.getMachine().getName() : "N/A");
                row.createCell(9).setCellValue(d.getOperateur() != null ? d.getOperateur().getUsername() : "N/A");
                row.createCell(10).setCellValue(d.getControleur() != null ? d.getControleur().getUsername() : "N/A");
                row.createCell(11).setCellValue(d.getDuree_en_minutes() != null ? d.getDuree_en_minutes() : 0);
                row.createCell(12).setCellValue(nullToNA(d.getEtq()));
                row.createCell(13).setCellValue(d.getStarted() != null && d.getStarted());
                row.createCell(14).setCellValue(d.getFinished() != null && d.getFinished());
                row.createCell(15).setCellValue(d.getNombre_produit_controle() != null ? d.getNombre_produit_controle() : 0);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandesFinale.xlsx");
            headersResponse.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity
                    .ok()
                    .headers(headersResponse)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
        }
    }

    @PostMapping("/batch")
    public List<DemandeFinale> createBatchDemandesFinale(@RequestBody List<DemandeFinale> demandesFinale) {
        return demandeFinaleService.createBatchDemandesFinale(demandesFinale);
    }

    // Utility
    private String nullToNA(String value) {
        return (value != null && !value.isBlank()) ? value : "N/A";
    }
}
