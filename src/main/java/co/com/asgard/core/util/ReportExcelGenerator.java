package co.com.asgard.core.util;

import co.com.asgard.core.dto.ReportResponseDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportExcelGenerator {

    public static byte[] generateExcel(ReportResponseDTO report) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");

            // Encabezados del reporte
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Total Pedidos Despachados",
                    "Entregas Exitosas",
                    "Pedidos Retrasados",
                    "Causa Retraso",
                    "Porcentaje Eficiencia"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(getHeaderStyle(workbook));
            }

            // Insertar los datos del reporte
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(report.getTotalPedidosDespachados());
            dataRow.createCell(1).setCellValue(report.getEntregasExitosas());
            dataRow.createCell(2).setCellValue(report.getPedidosRetrasados());
            dataRow.createCell(3).setCellValue(report.getCausaRetraso() != null ? report.getCausaRetraso() : "No especificado");
            dataRow.createCell(4).setCellValue(report.getPorcentajeEficiencia());

            // Ajustar columnas automáticamente
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convertir a un array de bytes
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }
    }

    private static CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}