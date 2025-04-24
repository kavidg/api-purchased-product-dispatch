package co.com.asgard.core.controller;

import co.com.asgard.core.config.LoggerContext;
import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;
import co.com.asgard.core.service.IReportService;
import co.com.asgard.core.util.Constants;
import co.com.asgard.core.util.ReportExcelGenerator;
import co.com.asgard.core.util.ReportPdfGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("${app.api.base-path}/reports")
public class ReportController {

    private final IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ReportResponseDTO> generateReport(
            @RequestBody ReportRequestDTO request,
            @RequestHeader(value = Constants.X_ID_USER, required = false) String idUser,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        ReportResponseDTO response = reportService.generateReport(request);
        log.info("[generateReport] Report generated successfully for User: {}", idUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports(
            @RequestHeader(value = Constants.X_ID_USER, required = false) String idUser,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        List<ReportDTO> reports = reportService.getAllReports();
        log.info("[getAllReports] {} reports retrieved for User: {}", reports.size(), idUser);
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/export/pdf-report")
    public ResponseEntity<byte[]> exportReportPdf(
            @RequestBody ReportRequestDTO request,
            @RequestHeader(value = Constants.X_ID_USER, required = false) String idUser,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        try {
            ReportResponseDTO report = reportService.generateReport(request);
            byte[] pdfBytes = ReportPdfGenerator.generatePDF(report);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            log.info("[exportReportAsPDF] PDF generated successfully for User: {}", idUser);
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            log.error("[exportReportAsPDF] Error generating PDF for User: {} - Error: {}", idUser, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/export/excel-report")
    public ResponseEntity<byte[]> exportReportExcel(
            @RequestBody ReportRequestDTO request,
            @RequestHeader(value = Constants.X_ID_USER, required = false) String idUser,
            @RequestHeader(value = Constants.X_ORIGIN_CHANNEL, required = false) String originChannel,
            @RequestHeader(value = Constants.X_CORRELATION_ID, required = false) String correlationId) {

        String correlation = correlationId != null ? correlationId : UUID.randomUUID().toString();
        LoggerContext.setUuid(correlation);
        LoggerContext.setBusiness(Constants.PURCHASED_PRODUCT_DISPATCH);
        LoggerContext.setApp(Constants.APP);

        try {
            ReportResponseDTO report = reportService.generateReport(request);
            byte[] excelBytes = ReportExcelGenerator.generateExcel(report);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "report.xlsx");

            log.info("[exportReportAsExcel] Excel generated successfully for User: {}", idUser);
            return ResponseEntity.ok().headers(headers).body(excelBytes);
        } catch (Exception e) {
            log.error("[exportReportAsExcel] Error generating Excel for User: {} - Error: {}", idUser, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}