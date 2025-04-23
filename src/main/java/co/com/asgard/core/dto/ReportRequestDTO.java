package co.com.asgard.core.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long carrierId;
    private Long clientId;
    private String orderStatus;
}