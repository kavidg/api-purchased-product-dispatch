package co.com.asgard.core.model;

import co.com.asgard.core.enums.StatusProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    private String name;

    private String description;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock = 0;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;

    @Enumerated(EnumType.STRING)
    private StatusProduct status;
}
