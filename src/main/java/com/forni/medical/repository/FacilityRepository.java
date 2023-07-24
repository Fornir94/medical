package com.forni.medical.repository;

import com.forni.medical.model.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findById(Long id);
    Optional<Facility> findByfacilityName(String facilityName);
}
