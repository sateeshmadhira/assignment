package com.ess.assignment.infrastructure.domain.sql.service.impl;
import com.ess.assignment.core.api.ApiResponse;
import com.ess.assignment.core.api.PaginationResponse;
import com.ess.assignment.core.dto.AssignmentDTO;
import com.ess.assignment.core.exception.GlobalExceptionHandler;
import com.ess.assignment.core.req.AssignmentRequest;
import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import com.ess.assignment.infrastructure.domain.sql.model.AssignmentEntity;
import com.ess.assignment.infrastructure.domain.sql.model.EmployeeEntity;
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
            //assignmentEntity.setAssignmentId(id);  // Preserve the ID to avoid creating a new record
            AssignmentEntity updatedEntity = assignmentRepository.save(assignmentEntity);

            return new ApiResponse(true, "Assignment updated successfully",
                    assignmentMapper.toDTO(updatedEntity), null);
        } else {
            throw new EntityNotFoundException("Assignment with ID " + id + " not found");
        }
    }

//    // Delete Assignment
//    public ApiResponse deleteAssignment(Long id) {
//        if (assignmentRepository.existsById(id)) {
//            assignmentRepository.deleteById(id);
//            return new ApiResponse(true, "Assignment deleted successfully", null, null);
//        } else {
//            throw new EntityNotFoundException("Assignment with ID " + id + " not found");
//        }
//    }

//    // Delete Assignment
//    public ApiResponse deleteAssignment(Long id) {
//        Optional<AssignmentEntity> assignmentEntityOptional = assignmentRepository.findById(id);
//        if (assignmentEntityOptional.isPresent()) {
//            assignmentRepository.deleteById(id);
//            return new ApiResponse(true, "Assignment deleted successfully", null, null);
//        }
//        return new ApiResponse(false, "Assignment not found", null, null);
//    }

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
        return new ApiResponse(true, "Assignments retrieved successfully", null, paginationResponse);
    }

    // Global Search with Pagination
    public ApiResponse globalSearch(String searchKey, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AssignmentEntity> assignmentEntities = assignmentRepository.globalSearch(searchKey, pageable);

        if (assignmentEntities.isEmpty()) {
            throw new EntityNotFoundException("No assignments found matching the criteria: " + searchKey);
        }

        return new ApiResponse(true, "Assignments found", null, createPaginationResponse(assignmentEntities));
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
