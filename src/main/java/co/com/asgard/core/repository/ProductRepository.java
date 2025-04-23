package co.com.asgard.core.repository;

import co.com.asgard.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByCode(String code);
    Optional<Product> findByCodeOrName(String code, String name);
}