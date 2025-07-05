package com.example.pfeaziz.controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.pfeaziz.model.Demande;
import com.example.pfeaziz.service.DemandeService;
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
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping
    public List<Demande> getAllDemandes() {
        return demandeService.getAllDemandes();
    }

    @GetMapping("/{id}")
    public Demande getDemandeById(@PathVariable Long id) {
        return demandeService.getDemandeById(id);
    }

    @PostMapping
    public Demande createDemande(@RequestBody Demande demande) {
        return demandeService.createDemande(demande);
    }

    @PutMapping("/{id}")
    public Demande updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        return demandeService.updateDemande(id, demande);
    }

    @DeleteMapping("/{id}")
    public void deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
    }

    // Endpoint for exporting demandes to Excel
    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportDemandesToExcel() throws IOException, ParseException {
        List<Demande> demandes = demandeService.getAllDemandes();
        if (demandes == null) {
            demandes = new ArrayList<>();
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Demandes");

            // Create date format style
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy")); // Set the desired date format

            // Create header row
            Row header = sheet.createRow(0);
            String[] headers = { 
                 "Operateur", "OF", "Sn", "Ilot", "Metier", "Article", "Date", "Time", "Controleur",
                 "quantite_controle_metier", "quantite_non_conforme", "Status", "Etq", "Defaut",
                 "Start Controle", "Finish Controle", "Duree De La Tache"
            };
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Input format

            // Create data rows
            int rowNum = 1;
            for (Demande demande : demandes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(demande.getOperateur() != null ? demande.getOperateur().getUsername() : "N/A");
                row.createCell(1).setCellValue(demande.getOf_demande() != null ? demande.getOf_demande() : "N/A");
                row.createCell(2).setCellValue(demande.getSn() != null ? demande.getSn() : "N/A");
                row.createCell(3).setCellValue(demande.getIlot() != null ? demande.getIlot().getName() : "N/A");
                row.createCell(4).setCellValue(demande.getMetier() != null ? demande.getMetier().getName() : "N/A");
                row.createCell(5).setCellValue(demande.getProgramme() != null ? demande.getProgramme().getName() : "N/A");

                // Set date cell
                if (demande.getDate() != null) {
                    Date date = inputDateFormat.parse(demande.getDate()); // Parse the date
                    row.createCell(6).setCellValue(date); // Insert the date as a Date object
                    row.getCell(6).setCellStyle(dateCellStyle); // Apply date format
                } else {
                    row.createCell(6).setCellValue("N/A");
                }

                row.createCell(7).setCellValue(demande.getTime() != null ? demande.getTime() : "N/A");
                row.createCell(8).setCellValue(demande.getControleur() != null ? demande.getControleur().getUsername() : "N/A");
                row.createCell(9).setCellValue(demande.getQuantite_controle_metier() != null ? demande.getQuantite_controle_metier() : "N/A");
                row.createCell(10).setCellValue(demande.getQuantite_non_conforme() != null ? demande.getQuantite_non_conforme() : "N/A");
                row.createCell(11).setCellValue(demande.getStatus() != null ? demande.getStatus() : "N/A");
                row.createCell(12).setCellValue(demande.getEtq() != null ? demande.getEtq() : "N/A");
                row.createCell(13).setCellValue(demande.getDefaut() != null ? demande.getDefaut() : "N/A");
                row.createCell(14).setCellValue(demande.getStartcontrole() != null ? demande.getStartcontrole() : "N/A");
                row.createCell(15).setCellValue(demande.getFinishcontrole() != null ? demande.getFinishcontrole() : "N/A");
                row.createCell(16).setCellValue(demande.getDureeDeLaTache() != null ? demande.getDureeDeLaTache() : "N/A");
            }

            // Write output to ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            // Set response headers
            HttpHeaders headersResponse = new HttpHeaders();
            headersResponse.add("Content-Disposition", "attachment; filename=demandes.xlsx");
            headersResponse.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return response
            return ResponseEntity
                    .ok()
                    .headers(headersResponse)
                    .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
        }
    }

    @PostMapping("/batch")
    public List<Demande> createBatchDemandes(@RequestBody List<Demande> demandes) {
        return demandeService.createBatchDemandes(demandes);
    }
}
