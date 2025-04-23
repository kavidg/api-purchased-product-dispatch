package co.com.asgard.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportResponseDTO {
    private int totalPedidosDespachados;
    private int entregasExitosas;
    private int pedidosRetrasados;
    private String causaRetraso;
    private double porcentajeEficiencia;
}