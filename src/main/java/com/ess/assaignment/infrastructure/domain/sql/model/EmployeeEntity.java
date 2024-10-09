package com.ess.assaignment.infrastructure.domain.sql.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "EMPLOYEE")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Column(name = "EMPLOYEE_EMAIL")
    private String email;

    @Column(name = "EMPLOYEE_MOBILE_NO")
    private String mobileNo;

    @ManyToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;
}
