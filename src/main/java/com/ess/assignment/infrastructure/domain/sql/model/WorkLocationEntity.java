package com.ess.assignment.infrastructure.domain.sql.model;

import com.ess.assignment.core.utils.WorkType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ASSIGNMENT_WORKLOCATION")
public class WorkLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkType workType;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "REPORTING_MANAGER_NAME")
    private String reportingManagerName;

    @Column(name = "REPORTING_MANAGER_NUMBER")
    private String reportingManagerNumber;

    @Column(name = "REPORTING_MANAGER_EMAIL")
    private String reportingManagerEmail;

    @OneToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;
}
