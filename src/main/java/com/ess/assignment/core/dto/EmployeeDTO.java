package com.ess.assignment.core.dto;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String employeeId;
    private Long assignmentIdRef;
    private String name;
    private String email;
    private String mobileNo;
}
