package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@RequiredArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transportista_id", nullable = false)
    private Carrier carrier;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Client client;

    @Column(name = "date_office", nullable = false)
    private LocalDateTime dateOffice;

    @Column(name = "delivery_status", nullable = false, length = 50)
    private String deliveryStatus;

    @Column(nullable = false, length = 255)
    private String destination;

    @Column(columnDefinition = "TEXT")
    private String details;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt = LocalDateTime.now();

}