package co.com.asgard.core.dto;

import lombok.Data;

@Data
public class StockDTO {
    private String code;
    private String name;
    private int quantityAvailable;
    private String location;
    private String status; // Disponible, Reservado, Agotado
}