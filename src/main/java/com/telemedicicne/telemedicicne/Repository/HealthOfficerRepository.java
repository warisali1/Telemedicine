package com.telemedicicne.telemedicicne.Repository;




import com.telemedicicne.telemedicicne.Entity.Doctor;
import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthOfficerRepository extends JpaRepository<HealthOfficer, Long> {

//    public Optional<HealthOfficer> findByEmail(String email);
List<HealthOfficer> findByRoles_Name(String roleName);

    public Optional<HealthOfficer> findByEmail(String email);

    HealthOfficer findByDocHsId(Long refId); // Add this method
    @Query("SELECT d FROM HealthOfficer d WHERE d.email = :email")
    HealthOfficer findByEmailDirect(@Param("email") String email);


}
