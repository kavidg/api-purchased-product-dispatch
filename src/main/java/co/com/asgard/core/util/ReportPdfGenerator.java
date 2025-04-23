package co.com.asgard.core.util;

import co.com.asgard.core.dto.ReportResponseDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class ReportPdfGenerator {

    public static byte[] generatePDF(ReportResponseDTO report) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("Reporte de Despachos", titleFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Total Pedidos Despachados: " + report.getTotalPedidosDespachados(), contentFont));
            document.add(new Paragraph("Entregas Exitosas: " + report.getEntregasExitosas(), contentFont));
            document.add(new Paragraph("Pedidos Retrasados: " + report.getPedidosRetrasados(), contentFont));
            document.add(new Paragraph("Causa de Retraso: " + report.getCausaRetraso(), contentFont));
            document.add(new Paragraph("Porcentaje de Eficiencia: " + report.getPorcentajeEficiencia() + "%", contentFont));

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}