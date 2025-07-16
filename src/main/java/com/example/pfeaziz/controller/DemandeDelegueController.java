package com.example.pfeaziz.controller;

import com.example.pfeaziz.model.DemandeDelegue;
import com.example.pfeaziz.service.DemandeDelegueService;
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
@RequestMapping("/api/demandes_delegue")
public class DemandeDelegueController {

    @Autowired
    private DemandeDelegueService demandeDelegueService;

    // 🟢 Get all
    @GetMapping
    public List<DemandeDelegue> getAllDemandesDelegue() {
        return demandeDelegueService.getAllDemandesDelegue();
    }

    // 🟡 Get by ID
    @GetMapping("/{id}")
    public DemandeDelegue getDemandeDelegueById(@PathVariable Long id) {
        return demandeDelegueService.getDemandeDelegueById(id);
    }

    // 🟢 Create
    @PostMapping
    public DemandeDelegue createDemandeDelegue(@RequestBody DemandeDelegue demandeDelegue) {
        return demandeDelegueService.createDemandeDelegue(demandeDelegue);
    }

    // 🟠 Update
    @PutMapping("/{id}")
    public DemandeDelegue updateDemandeDelegue(@PathVariable Long id, @RequestBody DemandeDelegue demandeDelegue) {
        return demandeDelegueService.updateDemandeDelegue(id, demandeDelegue);
    }

    // 🔴 Delete
    @DeleteMapping("/{id}")
    public void deleteDemandeDelegue(@PathVariable Long id) {
        demandeDelegueService.deleteDemandeDelegue(id);
    }

    // 📥 Batch insert
    @PostMapping("/batch")
    public List<DemandeDelegue> createBatchDemandesDelegue(@RequestBody List<DemandeDelegue> demandesDelegue) {
        return demandeDelegueService.createBatchDemandesDelegue(demandesDelegue);
    }

    // 📤 Excel Export
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesDelegueToExcel() throws IOException {
        List<DemandeDelegue> demandes = demandeDelegueService.getAllDemandesDelegue();
        if (demandes == null) demandes = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Demandes Delegue");

            String[] headers = {
                    "ID", "OF", "Date Demande", "Status", "Délégué À", "Date Délégation",
                    "Ilot", "Machine", "Opérateur", "Contrôleur",
                    "Finished", "Started", "Nb Produits Contrôlés", "ETQ"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (DemandeDelegue d : demandes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getId() != null ? d.getId() : 0);
                row.createCell(1).setCellValue(nullToNA(d.getOf_demande()));
                row.createCell(2).setCellValue(nullToNA(d.getDate_demande()));
                row.createCell(3).setCellValue(nullToNA(d.getStatus()));
                row.createCell(4).setCellValue(d.getDelegatedTo() != null ? d.getDelegatedTo().getUsername() : "N/A");
                row.createCell(5).setCellValue(nullToNA(d.getDelegationDate()));
                row.createCell(6).setCellValue(d.getIlot() != null ? d.getIlot().getName() : "N/A");
                row.createCell(7).setCellValue(d.getMachine() != null ? d.getMachine().getName() : "N/A");
                row.createCell(8).setCellValue(d.getOperateur() != null ? d.getOperateur().getUsername() : "N/A");
                row.createCell(9).setCellValue(d.getControleur() != null ? d.getControleur().getUsername() : "N/A");
                row.createCell(10).setCellValue(d.getFinished() != null && d.getFinished());
                row.createCell(11).setCellValue(d.getStarted() != null && d.getStarted());
                row.createCell(12).setCellValue(d.getNombre_produit_controle() != null ? d.getNombre_produit_controle() : 0);
                row.createCell(13).setCellValue(nullToNA(d.getEtq()));
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandesDelegue.xlsx");
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
