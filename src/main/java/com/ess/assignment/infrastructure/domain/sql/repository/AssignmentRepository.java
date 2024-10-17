package com.ess.assignment.infrastructure.domain.sql.repository;

import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import com.ess.assignment.infrastructure.domain.sql.model.AssignmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity,Long> {

    // Search by Assignment Code
    List<AssignmentEntity> findByAssignmentCode(String assignmentCode);

    // Search by ProjectId
    List<AssignmentEntity> findByProjectId(String projectId);

    // Search by Status
    List<AssignmentEntity> findByStatus(Status status);

    // Search by Placement Type
    List<AssignmentEntity> findByPlacementType(PlacementType placementType);

    // Custom query for searching by work location's country
    @Query("SELECT a FROM AssignmentEntity a WHERE a.workLocationEntity.country = :country")
    List<AssignmentEntity> findByWorkLocationCountry(@Param("country") String country);

    // Custom query for searching by employee name
    @Query("SELECT a FROM AssignmentEntity a JOIN a.employeeEntity e WHERE e.name LIKE %:name%")
    List<AssignmentEntity> findByEmployeeName(@Param("name") String name);

    // Custom query for searching by billing rate
    @Query("SELECT a FROM AssignmentEntity a WHERE a.billingEntity.payRate = :payRate")
    List<AssignmentEntity> findByBillingPayRate(@Param("payRate") double payRate);

    // Custom query for filtering by start and end date range
    @Query("SELECT a FROM AssignmentEntity a WHERE a.startDate >= :startDate AND a.endDate <= :endDate")
    List<AssignmentEntity> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    // Global search query to match any of the criteria: assignmentCode, country, assignmentTitle, status
    @Query("SELECT a FROM AssignmentEntity a " +
            "LEFT JOIN a.workLocationEntity w " +
            "WHERE (:searchKey IS NULL OR a.assignmentCode LIKE %:searchKey% " +
            "OR w.country LIKE %:searchKey% " +
            "OR a.assignmentTitle LIKE %:searchKey%)")
    Page<AssignmentEntity> globalSearch(@Param("searchKey") String searchKey,Pageable pageable);

// Fetch the latest assignment by ID in descending order
    Optional<AssignmentEntity> findTopByOrderByAssignmentIdDesc();

}
