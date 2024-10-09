package com.ess.assaignment.core.dto;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String employeeId;
    private Long linkId;
    private String name;
    private String email;
    private String mobileNo;
}
