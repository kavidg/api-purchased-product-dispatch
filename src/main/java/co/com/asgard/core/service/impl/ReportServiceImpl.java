package co.com.asgard.core.service.impl;

import co.com.asgard.core.config.LoggerContext;
import co.com.asgard.core.config.LoggerPrinter;
import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;
import co.com.asgard.core.model.Report;
import co.com.asgard.core.repository.ReportRepository;
import co.com.asgard.core.service.IReportService;
import co.com.asgard.core.enums.LogLevel;
import co.com.asgard.core.enums.ProcessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService {

    private final LoggerPrinter loggerPrinter;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, LoggerPrinter loggerPrinter) {
        this.reportRepository = reportRepository;
        this.loggerPrinter = loggerPrinter;
    }

    @Override
    public ReportResponseDTO generateReport(ReportRequestDTO request) {
        String correlationId = LoggerContext.getUuid();

        try {
            loggerPrinter.log(LogLevel.INFO, "Generating report",
                    "Start Date: " + request.getStartDate() +
                            ", End Date: " + request.getEndDate() +
                            ", Carrier ID: " + request.getCarrierId() +
                            ", Client ID: " + request.getClientId() +
                            ", Order Status: " + request.getOrderStatus() +
                            ", Correlation ID: " + correlationId,
                    "200", LoggerContext.getBusiness(), ProcessType.INPUT);

            LocalDateTime startDate = request.getStartDate().atStartOfDay();
            LocalDateTime endDate = request.getEndDate().atTime(LocalTime.MAX);

            Long carrierId = request.getCarrierId();
            Long clientId = request.getClientId();
            String orderStatus = (request.getOrderStatus() != null && !request.getOrderStatus().isEmpty()) ? request.getOrderStatus() : null;

            List<Report> reports = reportRepository.findReports(startDate, endDate, carrierId, clientId, orderStatus);

            if (reports.isEmpty()) {
                loggerPrinter.log(LogLevel.WARN, "No reports found",
                        "No records found for the given period. Correlation ID: " + correlationId,
                        "404", LoggerContext.getBusiness(), ProcessType.OUTPUT);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No records available for the selected period.");
            }

            long successfulDeliveries = reports.stream().filter(r -> "Delivered".equalsIgnoreCase(r.getDeliveryStatus())).count();
            long delayedOrders = reports.stream().filter(r -> "Delayed".equalsIgnoreCase(r.getDeliveryStatus())).count();
            int totalOrders = reports.size();
            double efficiencyPercentage = totalOrders > 0 ? (successfulDeliveries * 100.0 / totalOrders) : 0.0;
            String delayReason = delayedOrders > 0 ? "Some deliveries were delayed" : "No delays";

            ReportResponseDTO response = new ReportResponseDTO(totalOrders, (int) successfulDeliveries, (int) delayedOrders, delayReason, efficiencyPercentage);

            loggerPrinter.log(LogLevel.INFO, "Report generated successfully",
                    "Total Orders: " + totalOrders +
                            ", Successful Deliveries: " + successfulDeliveries +
                            ", Delayed Orders: " + delayedOrders +
                            ", Efficiency: " + efficiencyPercentage +
                            ", Correlation ID: " + correlationId,
                    "200", LoggerContext.getBusiness(), ProcessType.OUTPUT);

            return response;

        } catch (ResponseStatusException e) {
            loggerPrinter.log(LogLevel.ERROR, "Error 404 generating report",
                    "Message: " + e.getReason() + ", Correlation ID: " + correlationId,
                    "404", LoggerContext.getBusiness(), ProcessType.OUTPUT);
            throw e;
        } catch (Exception e) {
            loggerPrinter.log(LogLevel.ERROR, "Error 500 generating report",
                    "Exception: " + e.getMessage() + ", Stack Trace: " + Arrays.toString(e.getStackTrace()) + ", Correlation ID: " + correlationId,
                    "500", LoggerContext.getBusiness(), ProcessType.OUTPUT);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

    @Override
    public List<ReportDTO> getAllReports() {
        String correlationId = LoggerContext.getUuid();
        loggerPrinter.log(LogLevel.INFO, "Fetching all reports",
                "Correlation ID: " + correlationId,
                "200", LoggerContext.getBusiness(), ProcessType.INPUT);

        List<Report> reports = reportRepository.findAll();

        loggerPrinter.log(LogLevel.INFO, "Reports retrieved successfully",
                "Total Reports: " + reports.size() + ", Correlation ID: " + correlationId,
                "200", LoggerContext.getBusiness(), ProcessType.OUTPUT);

        return reports.stream().map(report ->
                new ReportDTO(
                        report.getId(),
                        report.getCarrier() != null ? report.getCarrier().getName() : "N/A",
                        report.getClient() != null ? report.getClient().getName() : "N/A",
                        report.getDateOffice(),
                        report.getDeliveryStatus(),
                        report.getDestination(),
                        report.getDetails()
                )
        ).collect(Collectors.toList());
    }

@Override
public byte[] generateExcelReport(ReportRequestDTO request) throws IOException {

        throw new UnsupportedOperationException("Unimplemented method 'generateExcelReport'");
}

}