package com.example.pfeaziz.controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.pfeaziz.model.DemandeDelegue;
import com.example.pfeaziz.service.DemandeDelegueService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/demandes_delegue")
public class DemandeDelegueController {

    @Autowired
    private DemandeDelegueService demandeDelegueService;

    @GetMapping
    public List<DemandeDelegue> getAllDemandesDelegue() {
        return demandeDelegueService.getAllDemandesDelegue();
    }

    @GetMapping("/{id}")
    public DemandeDelegue getDemandeDelegueById(@PathVariable Long id) {
        return demandeDelegueService.getDemandeDelegueById(id);
    }

    @PostMapping
    public DemandeDelegue createDemandeDelegue(@RequestBody DemandeDelegue demandeDelegue) {
        return demandeDelegueService.createDemandeDelegue(demandeDelegue);
    }

    @PutMapping("/{id}")
    public DemandeDelegue updateDemandeDelegue(@PathVariable Long id, @RequestBody DemandeDelegue demandeDelegue) {
        return demandeDelegueService.updateDemandeDelegue(id, demandeDelegue);
    }

    @DeleteMapping("/{id}")
    public void deleteDemandeDelegue(@PathVariable Long id) {
        demandeDelegueService.deleteDemandeDelegue(id);
    }

    // Excel export endpoint
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesDelegueToExcel() throws IOException, ParseException {
        List<DemandeDelegue> demandesDelegue = demandeDelegueService.getAllDemandesDelegue();
        if (demandesDelegue == null) {
            demandesDelegue = new ArrayList<>();
        }

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Input date format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Excel date format

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DemandesDelegue");

            // Date format for Excel cells
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Create header row
            Row header = sheet.createRow(0);
            String[] headers = { 
                "Controleur", "OF", "Sn", "Ilot", "Metier", "Article", "Date", "Time", 
                "Status", "Etq","Quantite Controle Metier", "Quantite Non Conforme", "Defaut", 
                "Start Controle", "Finish Controle", "Duree De La Tache"
            };
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (DemandeDelegue demandeDelegue : demandesDelegue) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(demandeDelegue.getControleur() != null ? demandeDelegue.getControleur() : "N/A");
                row.createCell(1).setCellValue(demandeDelegue.getOf_demande() != null ? demandeDelegue.getOf_demande() : "N/A");
                row.createCell(2).setCellValue(demandeDelegue.getSn() != null ? demandeDelegue.getSn() : "N/A");
                row.createCell(3).setCellValue(demandeDelegue.getIlot() != null ? demandeDelegue.getIlot() : "N/A");
                row.createCell(4).setCellValue(demandeDelegue.getMetier() != null ? demandeDelegue.getMetier() : "N/A");
                row.createCell(5).setCellValue(demandeDelegue.getProgramme() != null ? demandeDelegue.getProgramme() : "N/A");

                // Handle date format for Excel
                String formattedDate = "N/A";
                if (demandeDelegue.getDate() != null) {
                    try {
                        Date date = inputDateFormat.parse(demandeDelegue.getDate()); // Parse input format
                        row.createCell(6).setCellValue(date);
                        row.getCell(6).setCellStyle(dateCellStyle); // Apply date style
                    } catch (ParseException e) {
                        // Handle exception or keep formattedDate as "N/A"
                        row.createCell(6).setCellValue(formattedDate);
                    }
                } else {
                    row.createCell(6).setCellValue(formattedDate);
                }

                row.createCell(7).setCellValue(demandeDelegue.getTime() != null ? demandeDelegue.getTime() : "N/A");
                row.createCell(8).setCellValue(demandeDelegue.getStatus() != null ? demandeDelegue.getStatus() : "N/A");
                row.createCell(9).setCellValue(demandeDelegue.getEtq() != null ? demandeDelegue.getEtq() : "N/A");
                row.createCell(10).setCellValue(demandeDelegue.getQuantite_non_conforme() != null ? demandeDelegue.getQuantite_non_conforme() : "N/A");
                row.createCell(11).setCellValue(demandeDelegue.getQuantite_controle_metier() != null ? demandeDelegue.getQuantite_controle_metier() : "N/A");
                row.createCell(12).setCellValue(demandeDelegue.getDefaut() != null ? demandeDelegue.getDefaut() : "N/A");
                row.createCell(13).setCellValue(demandeDelegue.getStartcontrole() != null ? demandeDelegue.getStartcontrole() : "N/A");
                row.createCell(14).setCellValue(demandeDelegue.getFinishcontrole() != null ? demandeDelegue.getFinishcontrole() : "N/A");
                row.createCell(15).setCellValue(demandeDelegue.getDureeDeLaTache() != null ? demandeDelegue.getDureeDeLaTache() : "N/A");
            }

            // Write to ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Set response headers
                        HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandesDelegue.xlsx");
            headersResponse.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return the response
            return ResponseEntity
                    .ok()
                    .headers(headersResponse)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
        }
    }

    @PostMapping("/batch")
    public List<DemandeDelegue> createBatchDemandesDelegue(@RequestBody List<DemandeDelegue> demandesDelegue) {
        return demandeDelegueService.createBatchDemandesDelegue(demandesDelegue);
    }
}

