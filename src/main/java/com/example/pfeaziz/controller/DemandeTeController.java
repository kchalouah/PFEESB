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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/demandes_te")
public class DemandeTeController {

    @Autowired
    private DemandeTeService demandeTeService;

    @GetMapping
    public List<DemandeTe> getAllDemandesTe() {
        return demandeTeService.getAllDemandesTe();
    }

    @GetMapping("/{id}")
    public DemandeTe getDemandeTeById(@PathVariable Long id) {
        return demandeTeService.getDemandeTeById(id);
    }

    @PostMapping
    public DemandeTe createDemandeTe(@RequestBody DemandeTe demandeTe) {
        return demandeTeService.createDemandeTe(demandeTe);
    }

    @PutMapping("/{id}")
    public DemandeTe updateDemandeTe(@PathVariable Long id, @RequestBody DemandeTe demandeTe) {
        return demandeTeService.updateDemandeTe(id, demandeTe);
    }

    @DeleteMapping("/{id}")
    public void deleteDemandeTe(@PathVariable Long id) {
        demandeTeService.deleteDemandeTe(id);
    }

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesTeToExcel() throws IOException {
        List<DemandeTe> demandesTe = demandeTeService.getAllDemandesTe();
        if (demandesTe == null) {
            demandesTe = new ArrayList<>();
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DemandesTe");

            // Create header row
            Row header = sheet.createRow(0);
            String[] headers = { 
                "Sn", "Operateur", "Article/Plan", "Date", "Time", "Controleur", "Machine", 
                "Status", "Start Controleur", "Finish Controle", 
                "Duree De La Tache", "Duree Attente"
            };
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Create data rows
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int rowNum = 1;
            for (DemandeTe demandeTe : demandesTe) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(demandeTe.getSn() != null ? demandeTe.getSn() : "N/A");
                row.createCell(1).setCellValue(demandeTe.getOperateur() != null ? demandeTe.getOperateur() : "N/A");
                row.createCell(2).setCellValue(demandeTe.getProgramme() != null ? demandeTe.getProgramme() : "N/A");
                
                // Format Date
                if (demandeTe.getDate() != null) {
                    try {
                        Date date = dateFormat.parse(demandeTe.getDate());
                        Cell dateCell = row.createCell(3);
                        dateCell.setCellValue(date);
                        CellStyle cellStyle = workbook.createCellStyle();
                        CreationHelper creationHelper = workbook.getCreationHelper();
                        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));
                        dateCell.setCellStyle(cellStyle);
                    } catch (ParseException e) {
                        row.createCell(3).setCellValue(demandeTe.getDate());
                    }
                } else {
                    row.createCell(3).setCellValue("N/A");
                }

                row.createCell(4).setCellValue(demandeTe.getTime() != null ? demandeTe.getTime() : "N/A");
                row.createCell(5).setCellValue(demandeTe.getControleur() != null ? demandeTe.getControleur() : "N/A");
                row.createCell(6).setCellValue(demandeTe.getMachine() != null ? demandeTe.getMachine() : "N/A");
                row.createCell(7).setCellValue(demandeTe.getStatus() != null ? demandeTe.getStatus() : "N/A");
                row.createCell(8).setCellValue(demandeTe.getStartcontrole() != null ? demandeTe.getStartcontrole() : "N/A");
                row.createCell(9).setCellValue(demandeTe.getFinishcontrole() != null ? demandeTe.getFinishcontrole() : "N/A");
                row.createCell(10).setCellValue(demandeTe.getDureeDeLaTache() != null ? demandeTe.getDureeDeLaTache() : "N/A");
                row.createCell(11).setCellValue(demandeTe.getDureeAttente() != null ? demandeTe.getDureeAttente() : "N/A");
            }

            // Write the output to a ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Set up the response headers
            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandesTe.xlsx");
            headersResponse.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return the response
            return ResponseEntity
                    .ok()
                    .headers(headersResponse)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
        }
    }

    @PostMapping("/batch")
    public List<DemandeTe> createBatchDemandesTe(@RequestBody List<DemandeTe> demandesTe) {
        return demandeTeService.createBatchDemandesTe(demandesTe);
    }
}
