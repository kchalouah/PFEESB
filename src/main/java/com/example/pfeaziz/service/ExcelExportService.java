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
            headerRow.createCell(3).setCellValue("Machine");
            headerRow.createCell(4).setCellValue("OF");
            headerRow.createCell(5).setCellValue("Date Demande");
            headerRow.createCell(6).setCellValue("Status");
            headerRow.createCell(7).setCellValue("Duree (min)");
            headerRow.createCell(8).setCellValue("Etq");
            headerRow.createCell(9).setCellValue("Started");
            headerRow.createCell(10).setCellValue("Finished");
            headerRow.createCell(11).setCellValue("Nombre Produit Controle");
            headerRow.createCell(12).setCellValue("Controleur");
            // Fill data rows
            int rowNum = 1;
            for (Demande demande : demandes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(demande.getId());
                row.createCell(1).setCellValue(demande.getOperateur() != null ? demande.getOperateur().getUsername() : "N/A");
                row.createCell(2).setCellValue(demande.getIlot() != null ? demande.getIlot().getName() : "N/A");
                row.createCell(3).setCellValue(demande.getMachine() != null ? demande.getMachine().getName() : "N/A");
                row.createCell(4).setCellValue(demande.getOf_demande() != null ? demande.getOf_demande() : "N/A");
                row.createCell(5).setCellValue(demande.getDate_demande() != null ? demande.getDate_demande() : "N/A");
                row.createCell(6).setCellValue(demande.getStatus() != null ? demande.getStatus() : "N/A");
                row.createCell(7).setCellValue(demande.getDuree_en_minutes() != null ? demande.getDuree_en_minutes() : 0);
                row.createCell(8).setCellValue(demande.getEtq() != null ? demande.getEtq() : "N/A");
                row.createCell(9).setCellValue(demande.getStarted() != null ? demande.getStarted() : false);
                row.createCell(10).setCellValue(demande.getFinished() != null ? demande.getFinished() : false);
                row.createCell(11).setCellValue(demande.getNombre_produit_controle() != null ? demande.getNombre_produit_controle() : 0);
                row.createCell(12).setCellValue(demande.getControleur() != null ? demande.getControleur().getUsername() : "N/A");
            }
            // Adjust column width
            for (int i = 0; i <= 12; i++) {
                sheet.autoSizeColumn(i);
            }
            // Write the output to the response's output stream
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        }
        outputStream.close();
    }
}
