package com.example.pfeaziz.controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.pfeaziz.model.DemandeFinale;
import com.example.pfeaziz.service.DemandeFinaleService;
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
@RequestMapping("/api/demandes_finale")
public class DemandeFinaleController {

    @Autowired
    private DemandeFinaleService demandeFinaleService;

    @GetMapping
    public List<DemandeFinale> getAllDemandesFinale() {
        return demandeFinaleService.getAllDemandesFinale();
    }

    @GetMapping("/{id}")
    public DemandeFinale getDemandeFinaleById(@PathVariable Long id) {
        return demandeFinaleService.getDemandeFinaleById(id);
    }

    @PostMapping
    public DemandeFinale createDemandeFinale(@RequestBody DemandeFinale demandeFinale) {
        return demandeFinaleService.createDemandeFinale(demandeFinale);
    }

    @PutMapping("/{id}")
    public DemandeFinale updateDemandeFinale(@PathVariable Long id, @RequestBody DemandeFinale demandeFinale) {
        return demandeFinaleService.updateDemandeFinale(id, demandeFinale);
    }

    @DeleteMapping("/{id}")
    public void deleteDemandeFinale(@PathVariable Long id) {
        demandeFinaleService.deleteDemandeFinale(id);
    }

    // Endpoint for exporting demandes to Excel
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesFinaleToExcel() throws IOException, ParseException {
        List<DemandeFinale> demandesFinale = demandeFinaleService.getAllDemandesFinale();
        if (demandesFinale == null) {
            demandesFinale = new ArrayList<>();
        }

        // Define date formatters
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Input date format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Desired output format

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DemandesFinale");

            // Create a date style for Excel
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Create header row
            Row header = sheet.createRow(0);
            String[] headers = { 
                "Controleur", "OF", "Sn", "Ilot", "Metier", "Article", "Date", "Time",
                "Status", "Etq", "Defaut", "Start Controle", "Finish Controle", 
                "Duree De La Tache"
            };
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (DemandeFinale demandeFinale : demandesFinale) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(demandeFinale.getControleur() != null ? demandeFinale.getControleur() : "N/A");
                row.createCell(1).setCellValue(demandeFinale.getOf_demande() != null ? demandeFinale.getOf_demande() : "N/A");
                row.createCell(2).setCellValue(demandeFinale.getSn() != null ? demandeFinale.getSn() : "N/A");
                row.createCell(3).setCellValue(demandeFinale.getIlot() != null ? demandeFinale.getIlot() : "N/A");
                row.createCell(4).setCellValue(demandeFinale.getMetier() != null ? demandeFinale.getMetier() : "N/A");
                row.createCell(5).setCellValue(demandeFinale.getProgramme() != null ? demandeFinale.getProgramme() : "N/A");

                // Handle date formatting for Excel
                String formattedDate = "N/A";
                if (demandeFinale.getDate() != null) {
                    try {
                        Date date = inputDateFormat.parse(demandeFinale.getDate());
                        row.createCell(6).setCellValue(date);
                        row.getCell(6).setCellStyle(dateCellStyle); // Apply date style
                    } catch (ParseException e) {
                        row.createCell(6).setCellValue(formattedDate);
                    }
                } else {
                    row.createCell(6).setCellValue(formattedDate);
                }

                row.createCell(7).setCellValue(demandeFinale.getTime() != null ? demandeFinale.getTime() : "N/A");
                row.createCell(8).setCellValue(demandeFinale.getStatus() != null ? demandeFinale.getStatus() : "N/A");
                row.createCell(9).setCellValue(demandeFinale.getEtq() != null ? demandeFinale.getEtq() : "N/A");
                row.createCell(10).setCellValue(demandeFinale.getDefaut() != null ? demandeFinale.getDefaut() : "N/A");
                row.createCell(11).setCellValue(demandeFinale.getStartcontrole() != null ? demandeFinale.getStartcontrole() : "N/A");
                row.createCell(12).setCellValue(demandeFinale.getFinishcontrole() != null ? demandeFinale.getFinishcontrole() : "N/A");
                row.createCell(13).setCellValue(demandeFinale.getDureeDeLaTache() != null ? demandeFinale.getDureeDeLaTache() : "N/A");
            }

            // Write to ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Set response headers
            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandesFinale.xlsx");
            headersResponse.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return the response
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
}
