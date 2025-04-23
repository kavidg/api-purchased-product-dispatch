package co.com.asgard.core.service;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;

import java.io.IOException;
import java.util.List;

public interface IReportService {

    ReportResponseDTO generateReport(ReportRequestDTO request);

    List<ReportDTO> getAllReports();

    byte[] generateExcelReport(ReportRequestDTO request) throws IOException;


}