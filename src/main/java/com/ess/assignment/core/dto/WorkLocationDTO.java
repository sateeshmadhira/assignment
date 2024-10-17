package com.ess.assignment.core.dto;
import com.ess.assignment.core.utils.WorkType;
import lombok.Data;

@Data
public class WorkLocationDTO {
    private Long id;
    private Long assignmentIdRef;
    private WorkType workType;
    private String country;
    private String zipCode;
    private String city;
    private String address;
    private String reportingManagerName;
    private String reportingManagerNumber;
    private String reportingManagerEmail;
}
