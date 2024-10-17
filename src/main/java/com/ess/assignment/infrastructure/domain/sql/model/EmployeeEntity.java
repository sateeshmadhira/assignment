package com.ess.assignment.infrastructure.domain.sql.model;

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

    @ManyToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;
}
