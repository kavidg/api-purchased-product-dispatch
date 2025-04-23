package co.com.asgard.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    private String carrier;
    private String client;
    private LocalDateTime dateOffice;
    private String estadoEntrega;
    private String destination;
    private String details;
}