package com.forni.medical.repository;

import com.forni.medical.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    Optional<Visit> findById(Long id);
    Optional<Visit> findByVisitDate(LocalDateTime visitDate);
}
