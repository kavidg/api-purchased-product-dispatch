package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String telephone;

    @Column(length = 100, unique = true)
    private String email;

    @Column(name = "date_registration", nullable = false, updatable = false)
    private LocalDateTime dateRegistration = LocalDateTime.now();

    // Relación con Reportes
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

}