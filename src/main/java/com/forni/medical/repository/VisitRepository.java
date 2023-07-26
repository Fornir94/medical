package com.forni.medical.repository;

import com.forni.medical.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v " +
            "FROM Visit v " +
            "WHERE v.visitStartDate <= :visitEndDate " +
            "AND v.visitEndDate >= :visitStartDate")
    List<Visit> findAllOverlapping(LocalDateTime visitStartDate, LocalDateTime visitEndDate);

    Optional<Visit> findById(Long id);
}
