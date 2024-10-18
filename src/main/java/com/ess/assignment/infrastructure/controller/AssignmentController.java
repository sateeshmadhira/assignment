package com.ess.assignment.infrastructure.controller;
import com.ess.assignment.core.resp.ApiResponse;
import com.ess.assignment.core.constants.AssignmentConstants;
import com.ess.assignment.core.req.AssignmentRequest;
import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import com.ess.assignment.infrastructure.domain.sql.service.impl.AssignmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping(AssignmentConstants.ASSIGNMENT_PATH_URL)
@CrossOrigin("*")
public class AssignmentController {

    @Autowired
    private AssignmentServiceImpl assignmentService;

    // Create Assignment
    @PostMapping
    public ResponseEntity<ApiResponse> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        ApiResponse response = assignmentService.createAssignment(assignmentRequest);
        return ResponseEntity.ok(response);
    }

    // Get Assignment by ID
    @GetMapping(AssignmentConstants.ID)
    public ResponseEntity<ApiResponse> getAssignmentById(@PathVariable("ID") Long id) {
        ApiResponse response = assignmentService.getAssignmentById(id);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(404).body(response);
    }

    // Endpoint to get all assignments with their counts
    @GetMapping(AssignmentConstants.SEARCH_BY_COUNT)
    public ResponseEntity<ApiResponse> getAllAssignmentsWithCounts() {
        ApiResponse response = assignmentService.getAllAssignmentsWithCounts();
        return ResponseEntity.ok(response);
    }

    // Update Assignment
    @PutMapping(AssignmentConstants.ID)
    public ResponseEntity<ApiResponse> updateAssignment(@PathVariable("ID") Long id, @RequestBody AssignmentRequest assignmentRequest) {
       ApiResponse response= assignmentService.updateAssignmentStatus(id,assignmentRequest);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(404).body(response);
    }

    // Soft delete assignment by updating isActive to 0 and status to COMPLETED
    @DeleteMapping(AssignmentConstants.ID)
    public ResponseEntity<ApiResponse> softDeleteAssignment(@PathVariable("ID") Long assignmentId) {
       ApiResponse response= assignmentService.softDeleteAssignment(assignmentId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(404).body(response);
    }

    // Get All Assignments with Pagination
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAssignments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.getAllAssignments(page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Assignment Code with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_CODE)
    public ResponseEntity<ApiResponse> searchByAssignmentCode(
            @RequestParam String assignmentCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.searchByAssignmentCode(assignmentCode, page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Project ID with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_PROJ_ID)
    public ResponseEntity<ApiResponse> searchByProjectId(
            @RequestParam String projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.searchByProjectId(projectId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Work Location Country with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_LOCATION)
    public ResponseEntity<ApiResponse> searchByWorkLocationCountry(
            @RequestParam String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.searchByWorkLocationCountry(country, page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Status with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_STATUS)
    public ResponseEntity<ApiResponse> searchByStatus(
            @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.searchByStatus(status, page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Placement Type with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_PLACEMENT)
    public ResponseEntity<ApiResponse> searchByPlacementType(
            @RequestParam PlacementType placementType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.searchByPlacementType(placementType, page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Date Range with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_DATES)
    public ResponseEntity<ApiResponse> searchByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.searchByDateRange(startDate, endDate, page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Global Search with Pagination
    @GetMapping(AssignmentConstants.SEARCH_BY_SEARCH)
    public ResponseEntity<ApiResponse> globalSearch(
            @RequestParam String searchKey,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.globalSearch(searchKey, page, pageSize);
        return ResponseEntity.ok(response);
    }
}
