package co.com.asgard.core.repository;

import co.com.asgard.core.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByCarrierId(Long carrierId);
    Optional<Shipment> findByOrderId(Long orderId);
}