package co.com.asgard.core.repository;

import co.com.asgard.core.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserId(Long userId);

    @Query("SELECT r FROM Report r WHERE " +
            "(COALESCE(:startDate, r.dateOffice) <= r.dateOffice) AND " +
            "(COALESCE(:endDate, r.dateOffice) >= r.dateOffice) AND " +
            "(COALESCE(:carrierId, r.carrier.id) = r.carrier.id) AND " +
            "(COALESCE(:clientId, r.client.id) = r.client.id) AND " +
            "(COALESCE(:orderStatus, r.deliveryStatus) = r.deliveryStatus)")
    List<Report> findReports(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             @Param("carrierId") Long carrierId,
                             @Param("clientId") Long clientId,
                             @Param("orderStatus") String orderStatus);
}