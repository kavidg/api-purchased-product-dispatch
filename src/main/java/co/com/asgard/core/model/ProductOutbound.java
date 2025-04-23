package co.com.asgard.core.model;

import co.com.asgard.core.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_outbound")
@Getter
@Setter
@RequiredArgsConstructor
public class ProductOutbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeRegister; // Número de registro único generado

    @ManyToOne
    private Product product;

    private Integer quantity;

    private String destination;

    private LocalDate date;

    @ManyToOne
    private AppUser responsible;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private java.time.LocalDateTime statusUpdatedAt;
 
}