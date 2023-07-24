package com.forni.medical.repository;

import com.forni.medical.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findById(Long id);
    @Query("SELECT COUNT(d) > 0 " +
            "FROM Doctor d " +
            "JOIN d.facilities c " +
            "WHERE d.id = :doctorId " +
            "AND c.id = :facilityId")
    boolean isDoctorAddToFacility(Long doctorId,Long facilityId);
}
