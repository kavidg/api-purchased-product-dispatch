package co.com.asgard.core.repository;

import co.com.asgard.core.model.Historical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalRepository extends JpaRepository<Historical, Long> {
}