package com.forni.medical.repository;

import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findById(Long id);

    Optional<Facility> findByName(String facilityName);

    @Query("SELECT d FROM Facility f JOIN f.doctors d WHERE f.id = :facilityId")
    List<Doctor> findDoctorsByFacilityId(@Param("facilityId") Long facilityId);
}
