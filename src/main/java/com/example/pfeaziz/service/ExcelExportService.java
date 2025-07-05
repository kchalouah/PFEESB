package com.example.pfeaziz.service;


import com.example.pfeaziz.model.Demande;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public void exportDemandesToExcel(HttpServletResponse response, List<Demande> demandes) throws IOException {
        ServletOutputStream outputStream;
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Demandes");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Operateur");
            headerRow.createCell(2).setCellValue("Ilot");
            headerRow.createCell(3).setCellValue("Metier");
            headerRow.createCell(4).setCellValue("Date Demande");
            headerRow.createCell(5).setCellValue("Time Demande");
            headerRow.createCell(6).setCellValue("Controleur");
            headerRow.createCell(7).setCellValue("Status");
            headerRow.createCell(8).setCellValue("Defaut");
            headerRow.createCell(9).setCellValue("Start Controle");
            headerRow.createCell(10).setCellValue("Finish Controle");
            headerRow.createCell(11).setCellValue("Duree De La Tache");
            headerRow.createCell(15).setCellValue("Programme");
            // Fill data rows
            int rowNum = 1;
            for (Demande demande : demandes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(demande.getId());
                row.createCell(1).setCellValue(demande.getOperateur() != null ? demande.getOperateur().getUsername() : "N/A");
                row.createCell(2).setCellValue(demande.getIlot() != null ? demande.getIlot().getName() : "N/A");
                row.createCell(3).setCellValue(demande.getMetier() != null ? demande.getMetier().getName() : "N/A");
                row.createCell(4).setCellValue(demande.getDate());
                row.createCell(5).setCellValue(demande.getTime());
                row.createCell(6).setCellValue(demande.getControleur() != null ? demande.getControleur().getUsername() : "N/A");
                row.createCell(7).setCellValue(demande.getStatus());
                row.createCell(8).setCellValue(demande.getDefaut());
                row.createCell(9).setCellValue(demande.getStartcontrole());
                row.createCell(10).setCellValue(demande.getFinishcontrole());
                row.createCell(11).setCellValue(demande.getDureeDeLaTache());

                row.createCell(15).setCellValue(demande.getProgramme() != null ? demande.getProgramme().getName() : "N/A");
            }   // Adjust column width
            for (int i = 0; i < 17; i++) {
                sheet.autoSizeColumn(i);
            }   // Write the output to the response's output stream
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        }

        outputStream.close();
    }
}
