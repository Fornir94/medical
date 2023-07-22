package com.forni.medical.repository;

import com.forni.medical.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select v" +
    "from Visit v" +
    "Where v.visitStartDate <= :visitEndDate" +
    "and v.visitEndDate >= :visitStartDate")
    List<Visit> findAllOverLapping(LocalDateTime visitStartDate, LocalDateTime visitEndDate);
    Optional<Visit> findById(Long id);
    Optional<Visit> findByVisitStartDate(LocalDateTime visitStartDate);
}
