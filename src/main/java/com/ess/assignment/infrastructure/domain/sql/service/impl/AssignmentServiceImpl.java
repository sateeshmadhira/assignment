package com.ess.assignment.infrastructure.domain.sql.service.impl;
import com.ess.assignment.core.resp.ApiResponse;
import com.ess.assignment.core.resp.AssignmentCountResponse;
import com.ess.assignment.core.resp.PaginationResponse;
import com.ess.assignment.core.dto.AssignmentDTO;
import com.ess.assignment.core.exception.GlobalExceptionHandler;
import com.ess.assignment.core.req.AssignmentRequest;
import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import com.ess.assignment.infrastructure.domain.sql.model.AssignmentEntity;
import com.ess.assignment.infrastructure.domain.sql.repository.AssignmentRepository;
import com.ess.assignment.infrastructure.domain.sql.service.handler.AssignmentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssignmentServiceImpl {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    // Helper method to create Pagination Response
    private <T> PaginationResponse<T> createPaginationResponse(Page<T> page) {
        return new PaginationResponse<>(

                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.getContent()
        );
    }

    // Create Assignment
    public ApiResponse createAssignment(AssignmentRequest assignmentRequest) {
        try {
            Optional<AssignmentEntity> latestAssignment = assignmentRepository.findTopByOrderByAssignmentIdDesc();
            String assignmentCode = latestAssignment
                    .map(a -> {
                        String latestCode = a.getAssignmentCode().substring(4);
                        int nextCodeNumber = Integer.parseInt(latestCode) + 1;
                        return "ASG-" + String.format("%03d", nextCodeNumber);
                    })
                    .orElse("ASG-001");
            AssignmentEntity assignmentEntity = assignmentMapper.toEntity(assignmentRequest.getAssignmentDTO());
            assignmentEntity.setAssignmentCode(assignmentCode);
            if (assignmentEntity.getStatus() == Status.COMPLETED) {
                assignmentEntity.setIsActive(0);
            } else {
                assignmentEntity.setIsActive(1);
            }
            AssignmentEntity savedEntity = assignmentRepository.save(assignmentEntity);
            return new ApiResponse(true, "Assignment created successfully",
                    assignmentMapper.toDTO(savedEntity), null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create assignment: " + e.getMessage());
        }
    }
    // Get Assignment by ID
    public ApiResponse getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .map(entity -> new ApiResponse(true, "Assignment found", assignmentMapper.toDTO(entity), null))
                .orElseThrow(() -> new EntityNotFoundException("Assignment with ID " + id + " not found"));
    }
    // Get All Assignments With Counts
    public ApiResponse getAllAssignmentsWithCounts() {

        // Counts for active and inactive assignments
        long activeCount = assignmentRepository.countByIsActive(1);
        long inactiveCount = assignmentRepository.countByIsActive(0);

        // Counts for statuses
        long yetToStartCount = assignmentRepository.countByStatus(Status.YET_TO_START);
        long ongoingCount = assignmentRepository.countByStatus(Status.ONGOING);
        long completedCount = assignmentRepository.countByStatus(Status.COMPLETED);

        activeCount=yetToStartCount+ongoingCount;
        inactiveCount=completedCount;

        long totalAssignments = assignmentRepository.count();

        AssignmentCountResponse response = new AssignmentCountResponse
                (totalAssignments, activeCount, inactiveCount, yetToStartCount, ongoingCount, completedCount);

        return new ApiResponse(true,"Getting All Assignments",response,null);
    }

   // Update assignment status and set isActive accordingly
@Transactional
    public ApiResponse updateAssignmentStatus(Long id, AssignmentRequest assignmentRequest) {
        Optional<AssignmentEntity> optionalAssignment = assignmentRepository.findById(id);
             if (optionalAssignment.isPresent()) {
                 AssignmentEntity existingAssignment = optionalAssignment.get();
                 AssignmentDTO assignmentDTO = assignmentRequest.getAssignmentDTO();
                if (assignmentDTO.getAssignmentCode() != null) {
            existingAssignment.setAssignmentCode(assignmentDTO.getAssignmentCode());
              }
                 if (assignmentDTO.getAssignmentTitle() != null) {
            existingAssignment.setAssignmentTitle(assignmentDTO.getAssignmentTitle());
              }
                 if (assignmentDTO.getStatus() != null) {
              existingAssignment.setStatus(assignmentDTO.getStatus());
                 if (assignmentDTO.getStatus().equals(Status.COMPLETED)) {
                existingAssignment.setIsActive(0);  // Set as inactive
            } else {
                existingAssignment.setIsActive(1);  // Set as active
            }
        }
        AssignmentEntity updatedEntity = assignmentRepository.save(existingAssignment);
        return new ApiResponse(true, "Assignment updated successfully",
                assignmentMapper.toDTO(updatedEntity), null);
    } else {
        throw new EntityNotFoundException("Assignment with ID " + id + " not found");
    }
}
    // Soft Delete
    public ApiResponse softDeleteAssignment(Long assignmentId) {
        Optional<AssignmentEntity> assignmentOptional = assignmentRepository.findById(assignmentId);

        if (assignmentOptional.isEmpty()) {
            throw new EntityNotFoundException("Assignment not found with ID: " + assignmentId);
        }

        assignmentRepository.softDeleteAssignment(assignmentId);

        return new ApiResponse(true,"Soft Delete Success",null,null);
    }

    // Get All Assignments
    public ApiResponse getAllAssignments(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.unsorted());
        Page<AssignmentEntity> assignmentPage = assignmentRepository.findAll(pageable);
        List<AssignmentDTO> assignments = assignmentPage.getContent().stream()
                .map(assignmentMapper::toDTO)
                .toList();

        PaginationResponse<AssignmentDTO> paginationResponse = new PaginationResponse<>(
                assignmentPage.getNumber(),
                assignmentPage.getTotalPages(),
                assignmentPage.getTotalElements(),
                assignmentPage.getSize(),
                assignments
        );
        return new ApiResponse(true, "Assignments retrieved successfully",null,paginationResponse);
    }

    public ApiResponse globalSearch(String searchKey,int page,int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignmentEntities = assignmentRepository.globalSearch(searchKey,pageable);

        List<AssignmentDTO> assignmentDTOs = assignmentEntities.stream()
                .map(assignmentMapper::toDTO)
                .toList();

        PaginationResponse<AssignmentDTO> paginationResponse = new PaginationResponse<>(
                assignmentEntities.getNumber(),
                assignmentEntities.getTotalPages(),
                assignmentEntities.getTotalElements(),
                assignmentEntities.getSize(),
                assignmentDTOs
        );

        if (assignmentDTOs.isEmpty()) {
            return new ApiResponse(false, "No assignments found matching the criteria", null, paginationResponse);
        }

        return new ApiResponse(true, "Assignments found", null, paginationResponse);
    }

    // Search by Assignment Code with Pagination
    public ApiResponse searchByAssignmentCode(String assignmentCode, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByAssignmentCodeContainingIgnoreCase(assignmentCode, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with code: " + assignmentCode);
        }

        return new ApiResponse(true, "Assignments found by code", null, createPaginationResponse(assignments));
    }

    // Search by Project ID with Pagination
    public ApiResponse searchByProjectId(String projectId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByProjectIdContainingIgnoreCase(projectId, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with project ID: " + projectId);
        }

        return new ApiResponse(true, "Assignments found by project ID", null, createPaginationResponse(assignments));
    }

    // Search by Work Location Country with Pagination
    public ApiResponse searchByWorkLocationCountry(String country, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByWorkLocationCountryContainingIgnoreCase(country, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found in country: " + country);
        }

        return new ApiResponse(true, "Assignments found by country", null, createPaginationResponse(assignments));
    }

    // Search by Status with Pagination
    public ApiResponse searchByStatus(Status status, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByStatus(status, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with status: " + status);
        }

        return new ApiResponse(true, "Assignments found by status", null, createPaginationResponse(assignments));
    }

    // Search by Placement Type with Pagination
    public ApiResponse searchByPlacementType(PlacementType placementType, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByPlacementType(placementType, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with placement type: " + placementType);
        }

        return new ApiResponse(true, "Assignments found by placement type", null, createPaginationResponse(assignments));
    }

    // Search by Billing Pay Rate with Pagination
    public ApiResponse searchByBillingPayRate(double payRate, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByBillingPayRate(payRate, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with pay rate: " + payRate);
        }

        return new ApiResponse(true, "Assignments found by billing pay rate", null, createPaginationResponse(assignments));
    }

    // Search by Date Range with Pagination
    public ApiResponse searchByDateRange(Date startDate, Date endDate, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignments = assignmentRepository.findByDateRange(startDate, endDate, pageable);

        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found in the given date range.");
        }

        return new ApiResponse(true, "Assignments found by date range", null, createPaginationResponse(assignments));
    }
}
