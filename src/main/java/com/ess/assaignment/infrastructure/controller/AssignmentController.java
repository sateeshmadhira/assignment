package com.ess.assaignment.infrastructure.controller;
import com.ess.assaignment.core.api.ApiResponse;
import com.ess.assaignment.core.constants.AssignmentConstants;
import com.ess.assaignment.core.req.AssignmentRequest;
import com.ess.assaignment.core.utils.PlacementType;
import com.ess.assaignment.core.utils.Status;
import com.ess.assaignment.infrastructure.domain.sql.service.impl.AssignmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping(AssignmentConstants.ASSIGNMENT_PATH_URL)
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
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    // Update Assignment
    @PutMapping(AssignmentConstants.ID)
    public ResponseEntity<ApiResponse> updateAssignment(@PathVariable("ID") Long id, @RequestBody AssignmentRequest assignmentRequest) {
        ApiResponse response = assignmentService.updateAssignment(id, assignmentRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    // Delete Assignment
    @DeleteMapping(AssignmentConstants.ID)
    public ResponseEntity<ApiResponse> deleteAssignment(@PathVariable Long id) {
        ApiResponse response = assignmentService.deleteAssignment(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    // Get All Assignments with Pagination (default parameters)
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAssignments(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        ApiResponse response = assignmentService.getAllAssignments(page, pageSize);
        return ResponseEntity.ok(response);
    }

    // Search by Assignment Code
    @GetMapping("/search/code")
    public ResponseEntity<ApiResponse> searchByAssignmentCode(@RequestParam String assignmentCode) {
        return ResponseEntity.ok(assignmentService.searchByAssignmentCode(assignmentCode));
    }

    // Search by Status
    @GetMapping("/search/status")
    public ResponseEntity<ApiResponse> searchByStatus(@RequestParam Status status) {
        return ResponseEntity.ok(assignmentService.searchByStatus(status));
    }

    // Search by Placement Type
    @GetMapping("/search/placement")
    public ResponseEntity<ApiResponse> searchByPlacementType(@RequestParam PlacementType placementType) {
        return ResponseEntity.ok(assignmentService.searchByPlacementType(placementType));
    }

    // Search by Work Location Country
    @GetMapping("/search/location")
    public ResponseEntity<ApiResponse> searchByWorkLocationCountry(@RequestParam String country) {
        return ResponseEntity.ok(assignmentService.searchByWorkLocationCountry(country));
    }

    // Search by Employee Name
    @GetMapping("/search/employee")
    public ResponseEntity<ApiResponse> searchByEmployeeName(@RequestParam String name) {
        return ResponseEntity.ok(assignmentService.searchByEmployeeName(name));
    }

    // Search by Billing Pay Rate
    @GetMapping("/search/payrate")
    public ResponseEntity<ApiResponse> searchByBillingPayRate(@RequestParam double payRate) {
        return ResponseEntity.ok(assignmentService.searchByBillingPayRate(payRate));
    }

    // Search by Date Range
    @GetMapping("/search/dates")
    public ResponseEntity<ApiResponse> searchByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(assignmentService.searchByDateRange(startDate, endDate));
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse> globalSearch(
            @RequestParam(required = false) String assignmentCode,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String assignmentTitle,
            @RequestParam(required = false) Status status) {

        ApiResponse response = assignmentService.globalSearch(assignmentCode, country, assignmentTitle, status);
        return ResponseEntity.ok(response);
    }


}
