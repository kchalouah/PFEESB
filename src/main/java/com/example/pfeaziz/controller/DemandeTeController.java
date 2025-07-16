package com.example.pfeaziz.controller;

import com.example.pfeaziz.model.DemandeTe;
import com.example.pfeaziz.service.DemandeTeService;
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
@RequestMapping("/api/demandes_te")
public class DemandeTeController {

    @Autowired
    private DemandeTeService demandeTeService;

    // 🟢 Get all
    @GetMapping
    public List<DemandeTe> getAllDemandesTe() {
        return demandeTeService.getAllDemandesTe();
    }

    // 🟡 Get by ID
    @GetMapping("/{id}")
    public DemandeTe getDemandeTeById(@PathVariable Long id) {
        return demandeTeService.getDemandeTeById(id);
    }

    // 🟢 Create
    @PostMapping
    public DemandeTe createDemandeTe(@RequestBody DemandeTe demandeTe) {
        return demandeTeService.createDemandeTe(demandeTe);
    }

    // 🟠 Update
    @PutMapping("/{id}")
    public DemandeTe updateDemandeTe(@PathVariable Long id, @RequestBody DemandeTe demandeTe) {
        return demandeTeService.updateDemandeTe(id, demandeTe);
    }

    // 🔴 Delete
    @DeleteMapping("/{id}")
    public void deleteDemandeTe(@PathVariable Long id) {
        demandeTeService.deleteDemandeTe(id);
    }

    // 📥 Batch insert
    @PostMapping("/batch")
    public List<DemandeTe> createBatchDemandesTe(@RequestBody List<DemandeTe> demandesTe) {
        return demandeTeService.createBatchDemandesTe(demandesTe);
    }

    // 📤 Export to Excel
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesTeToExcel() throws IOException {
        List<DemandeTe> demandesTe = demandeTeService.getAllDemandesTe();
        if (demandesTe == null) demandesTe = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Demandes TE");

            String[] headers = {
                    "ID", "OF", "Date Demande", "Status", "TE Status",
                    "Ilot", "Machine", "Opérateur", "Contrôleur",
                    "Durée (min)", "ETQ", "Started", "Finished", "Nb Produits Contrôlés"
            };

            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (DemandeTe d : demandesTe) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getId() != null ? d.getId() : 0);
                row.createCell(1).setCellValue(nullToNA(d.getOf_demande()));
                row.createCell(2).setCellValue(nullToNA(d.getDate_demande()));
                row.createCell(3).setCellValue(nullToNA(d.getStatus()));
                row.createCell(4).setCellValue(nullToNA(d.getTeStatus()));
                row.createCell(5).setCellValue(d.getIlot() != null ? d.getIlot().getName() : "N/A");
                row.createCell(6).setCellValue(d.getMachine() != null ? d.getMachine().getName() : "N/A");
                row.createCell(7).setCellValue(d.getOperateur() != null ? d.getOperateur().getUsername() : "N/A");
                row.createCell(8).setCellValue(d.getControleur() != null ? d.getControleur().getUsername() : "N/A");
                row.createCell(9).setCellValue(d.getDuree_en_minutes() != null ? d.getDuree_en_minutes() : 0);
                row.createCell(10).setCellValue(nullToNA(d.getEtq()));
                row.createCell(11).setCellValue(d.getStarted() != null && d.getStarted());
                row.createCell(12).setCellValue(d.getFinished() != null && d.getFinished());
                row.createCell(13).setCellValue(d.getNombre_produit_controle() != null ? d.getNombre_produit_controle() : 0);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandesTe.xlsx");
            headersResponse.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .headers(headersResponse)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
        }
    }

    private String nullToNA(String value) {
        return (value != null && !value.isBlank()) ? value : "N/A";
    }
}
