package com.forni.medical.repository;

import com.forni.medical.model.entity.Doctor;
import com.forni.medical.model.entity.Facility;
import com.forni.medical.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findById(Long id);

    @Query("SELECT COUNT(d) > 0 " +
            "FROM Doctor d " +
            "JOIN d.facilities c " +
            "WHERE d.id = :doctorId " +
            "AND c.id = :facilityId")
    boolean isDoctorAddToFacility(Long doctorId, Long facilityId);

    @Query("SELECT v FROM Doctor d JOIN d.visits v WHERE d.id = :doctorId")
    List<Visit> findVisitsByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT v FROM Doctor d JOIN d.facilities v WHERE d.id = :doctorId")
    List<Facility> findFacilitiesByDoctorId(@Param("doctorId") Long doctorId);
}
