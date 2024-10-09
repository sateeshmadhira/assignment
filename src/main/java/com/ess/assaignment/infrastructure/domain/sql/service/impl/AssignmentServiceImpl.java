package com.ess.assaignment.infrastructure.domain.sql.service.impl;
import com.ess.assaignment.core.api.ApiResponse;
import com.ess.assaignment.core.api.PaginationResponse;
import com.ess.assaignment.core.dto.AssignmentDTO;
import com.ess.assaignment.core.exception.GlobalExceptionHandler;
import com.ess.assaignment.core.req.AssignmentRequest;
import com.ess.assaignment.core.utils.PlacementType;
import com.ess.assaignment.core.utils.Status;
import com.ess.assaignment.infrastructure.domain.sql.model.AssignmentEntity;
import com.ess.assaignment.infrastructure.domain.sql.repository.AssignmentRepository;
import com.ess.assaignment.infrastructure.domain.sql.service.handler.AssignmentMapper;
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

        AssignmentEntity assignmentEntity = assignmentMapper.toEntity(assignmentRequest.getAssignmentDTO());

        AssignmentEntity savedEntity = assignmentRepository.save(assignmentEntity);

        AssignmentDTO savedDTO = assignmentMapper.toDTO(savedEntity);

        return new ApiResponse(true, "Assignment created successfully", savedDTO, null);
    }

    // Get Assignment by ID
    public ApiResponse getAssignmentById(Long id) {
        Optional<AssignmentEntity> assignmentEntityOptional = assignmentRepository.findById(id);
        return assignmentEntityOptional
                .map(entity -> new ApiResponse(true, "Assignment found", assignmentMapper.toDTO(entity), null))
                .orElseThrow(() -> new EntityNotFoundException("Assignment with ID " + id + " not found"));
    }

    // Update Assignment
    public ApiResponse updateAssignment(Long id, AssignmentRequest assignmentRequest) {
        Optional<AssignmentEntity> assignmentEntityOptional = assignmentRepository.findById(id);
        if (assignmentEntityOptional.isPresent()) {
            AssignmentEntity assignmentEntity = assignmentMapper.toEntity(assignmentRequest.getAssignmentDTO());
            assignmentEntity = assignmentRepository.save(assignmentEntity);
            return new ApiResponse(true, "Assignment updated successfully", assignmentMapper.toDTO(assignmentEntity), null);
        }
        return new ApiResponse(false, "Assignment not found", null, null);
    }

    // Delete Assignment
    public ApiResponse deleteAssignment(Long id) {
        Optional<AssignmentEntity> assignmentEntityOptional = assignmentRepository.findById(id);
        if (assignmentEntityOptional.isPresent()) {
            assignmentRepository.deleteById(id);
            return new ApiResponse(true, "Assignment deleted successfully", null, null);
        }
        return new ApiResponse(false, "Assignment not found", null, null);
    }

    public ApiResponse getAllAssignments(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.unsorted());

        Page<AssignmentEntity> assignmentPage = assignmentRepository.findAll(pageable);
        List<AssignmentDTO> assignments = assignmentPage.getContent().stream()
                .map(assignmentMapper::toDTO)
                .toList();

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
        List<AssignmentDTO> assignmentDTOs = assignments.stream().map(assignmentMapper::toDTO).toList();
        return new ApiResponse(true, "Assignments found by code", assignmentDTOs, null);
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

    public ApiResponse globalSearch(String assignmentCode, String country, String assignmentTitle, Status status) {
        // Call repository global search method
        List<AssignmentEntity> assignmentEntities = assignmentRepository.globalSearch(assignmentCode, country, assignmentTitle, status);
        List<AssignmentDTO> assignmentDTOs = assignmentEntities.stream().map(assignmentMapper::toDTO).toList();

        if (assignmentDTOs.isEmpty()) {
            return new ApiResponse(false, "No assignments found matching the criteria", null, null);
        }

        return new ApiResponse(true, "Assignments found", assignmentDTOs, null);
    }
}
