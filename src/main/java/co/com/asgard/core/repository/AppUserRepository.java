package co.com.asgard.core.repository;

import co.com.asgard.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findByRole_Name(String roleName); // Por ejemplo, encontrar todos los carriers
}