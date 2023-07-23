package com.forni.medical.repository;

import com.forni.medical.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<Patient> findById(Long id);

}
