package com.ess.assignment.infrastructure.domain.sql.service.impl;
import com.ess.assignment.core.api.ApiResponse;
import com.ess.assignment.core.api.PaginationResponse;
import com.ess.assignment.core.dto.AssignmentDTO;
import com.ess.assignment.core.exception.GlobalExceptionHandler;
import com.ess.assignment.core.req.AssignmentRequest;
import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import com.ess.assignment.infrastructure.domain.sql.model.AssignmentEntity;
import com.ess.assignment.infrastructure.domain.sql.repository.AssignmentRepository;
import com.ess.assignment.infrastructure.domain.sql.service.handler.AssignmentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

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

    // Update Assignment
    public ApiResponse updateAssignment(Long id, AssignmentRequest assignmentRequest) {
        if (assignmentRepository.existsById(id)) {
            AssignmentEntity assignmentEntity = assignmentMapper.toEntity(assignmentRequest.getAssignmentDTO());
            assignmentEntity.setAssignmentId(id);  // Preserve the ID to avoid creating a new record
            AssignmentEntity updatedEntity = assignmentRepository.save(assignmentEntity);

            return new ApiResponse(true, "Assignment updated successfully",
                    assignmentMapper.toDTO(updatedEntity), null);
        } else {
            throw new EntityNotFoundException("Assignment with ID " + id + " not found");
        }
    }

    // Delete Assignment
    public ApiResponse deleteAssignment(Long id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
            return new ApiResponse(true, "Assignment deleted successfully", null, null);
        } else {
            throw new EntityNotFoundException("Assignment with ID " + id + " not found");
        }
    }

    // Get All Assignments with Pagination
    public ApiResponse getAllAssignments(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("assignmentCode").ascending());
        Page<AssignmentEntity> assignmentPage = assignmentRepository.findAll(pageable);

        List<AssignmentDTO> assignments = assignmentPage.getContent().stream()
                .map(assignmentMapper::toDTO)
                .collect(Collectors.toList());

        PaginationResponse<AssignmentDTO> paginationResponse = new PaginationResponse<>(
                assignments,
                assignmentPage.getNumber(),
                assignmentPage.getTotalPages(),
                assignmentPage.getTotalElements(),
                assignmentPage.getSize()
        );

        return new ApiResponse(true, "Assignments retrieved successfully", null, paginationResponse);
    }

    // Search by Assignment Code
    public ApiResponse searchByAssignmentCode(String assignmentCode) {
        List<AssignmentEntity> assignments = assignmentRepository.findByAssignmentCode(assignmentCode);
        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with code: " + assignmentCode);
        }

        List<AssignmentDTO> assignmentDTOs = assignments.stream()
                .map(assignmentMapper::toDTO)
                .collect(Collectors.toList());

        return new ApiResponse(true, "Assignments found by code", assignmentDTOs, null);
    }

    // Search by Project ID
    public ApiResponse searchByProjectId(String projectId) {
        List<AssignmentEntity> assignments = assignmentRepository.findByProjectId(projectId);
        if (assignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found with project ID: " + projectId);
        }

        List<AssignmentDTO> assignmentDTOs = assignments.stream()
                .map(assignmentMapper::toDTO)
                .collect(Collectors.toList());

        return new ApiResponse(true, "Assignments found by project ID", assignmentDTOs, null);
    }

    // Search by Status
    public ApiResponse searchByStatus(Status status) {
        List<AssignmentEntity> assignments = assignmentRepository.findByStatus(status);
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by status", assignmentDTOs, null);
    }

    // Search by Placement Type
    public ApiResponse searchByPlacementType(PlacementType placementType) {
        List<AssignmentEntity> assignments = assignmentRepository.findByPlacementType(placementType);
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by placement type", assignmentDTOs, null);
    }

    // Search by Work Location Country
    public ApiResponse searchByWorkLocationCountry(String country) {
        List<AssignmentEntity> assignments = assignmentRepository.findByWorkLocationCountry(country);
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by work location country", assignmentDTOs, null);
    }

    // Search by Employee Name
    public ApiResponse searchByEmployeeName(String name) {
        List<AssignmentEntity> assignments = assignmentRepository.findByEmployeeName(name);
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by employee name", assignmentDTOs, null);
    }

    // Search by Billing Pay Rate
    public ApiResponse searchByBillingPayRate(double payRate) {
        List<AssignmentEntity> assignments = assignmentRepository.findByBillingPayRate(payRate);
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by billing pay rate", assignmentDTOs, null);
    }

    // Search by Date Range
    public ApiResponse searchByDateRange(Date startDate, Date endDate) {
        List<AssignmentEntity> assignments = assignmentRepository.findByDateRange(startDate, endDate);
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by date range", assignmentDTOs, null);
    }

    // Global Search with Pagination
    public ApiResponse globalSearch(String searchKey, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignmentEntities = assignmentRepository.globalSearch(searchKey, pageable);

        if (assignmentEntities.isEmpty()) {
            throw new EntityNotFoundException("No assignments found matching the criteria: " + searchKey);
        }

        List<AssignmentDTO> assignmentDTOs = assignmentEntities.getContent().stream()
                .map(assignmentMapper::toDTO)
                .collect(Collectors.toList());

        PaginationResponse<AssignmentDTO> paginationResponse = new PaginationResponse<>(
                assignmentDTOs,
                assignmentEntities.getNumber(),
                assignmentEntities.getTotalPages(),
                assignmentEntities.getTotalElements(),
                assignmentEntities.getSize()
        );

        return new ApiResponse(true, "Assignments found", null, paginationResponse);
    }


}
