package com.ess.assignment.infrastructure.domain.sql.repository;

import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import com.ess.assignment.infrastructure.domain.sql.model.AssignmentEntity;
import com.ess.assignment.infrastructure.domain.sql.model.EmployeeEntity;
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

    // Get the latest assignment by ID in descending order
    Optional<AssignmentEntity> findTopByOrderByAssignmentIdDesc();

    // Find by assignment code with pagination
    Page<AssignmentEntity> findByAssignmentCodeContainingIgnoreCase(String assignmentCode, Pageable pageable);

    // Find by project ID with pagination
    Page<AssignmentEntity> findByProjectIdContainingIgnoreCase(String projectId, Pageable pageable);

    // Find by work location country with pagination
    @Query("SELECT a FROM AssignmentEntity a WHERE LOWER(a.workLocationEntity.country) LIKE LOWER(CONCAT('%', :country, '%'))")
    Page<AssignmentEntity> findByWorkLocationCountryContainingIgnoreCase(@Param("country") String country, Pageable pageable);

    // Find by status with pagination
    Page<AssignmentEntity> findByStatus(Status status, Pageable pageable);

    // Find by placement type with pagination
    Page<AssignmentEntity> findByPlacementType(PlacementType placementType, Pageable pageable);

    // Find by billing pay rate with pagination
    @Query("SELECT a FROM AssignmentEntity a WHERE a.billingEntity.payRate = :payRate")
    Page<AssignmentEntity> findByBillingPayRate(@Param("payRate") double payRate, Pageable pageable);

    // Find by date range with pagination
    @Query("SELECT a FROM AssignmentEntity a WHERE a.startDate >= :startDate AND a.endDate <= :endDate")
    Page<AssignmentEntity> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    // Global search query to match any of the criteria: assignmentCode, country, assignmentTitle
    @Query("SELECT a FROM AssignmentEntity a " +
            "LEFT JOIN a.workLocationEntity w " +
            "WHERE (:searchKey IS NULL OR " +
            "LOWER(a.assignmentCode) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "LOWER(w.country) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "LOWER(a.assignmentTitle) LIKE LOWER(CONCAT('%', :searchKey, '%')))")
    Page<AssignmentEntity> globalSearch(@Param("searchKey") String searchKey, Pageable pageable);

//    // Global search query to match any of the criteria: assignmentCode, country, assignmentTitle, status
//    @Query("SELECT a FROM AssignmentEntity a " +
//            "LEFT JOIN a.workLocationEntity w " +
//            "WHERE (:searchKey IS NULL OR a.assignmentCode LIKE %:searchKey% " +
//            "OR w.country LIKE %:searchKey% " +
//            "OR a.assignmentTitle LIKE %:searchKey%)")
//    Page<AssignmentEntity> globalSearch(@Param("searchKey") String searchKey,Pageable pageable);

}
