package co.com.asgard.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historicals")
@NoArgsConstructor
@AllArgsConstructor
public class Historical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String user;

    @Column(name = "date_consultation", nullable = false)
    private LocalDateTime dateConsultation;

}