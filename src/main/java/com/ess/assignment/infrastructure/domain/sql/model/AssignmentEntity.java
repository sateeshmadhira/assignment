package com.ess.assignment.infrastructure.domain.sql.model;

import com.ess.assignment.core.utils.HiringType;
import com.ess.assignment.core.utils.PlacementType;
import com.ess.assignment.core.utils.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "ASSIGNMENT")
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSIGNMENT_ID" ,nullable = false)
    private Long assignmentId;

    @Column(name = "ASSIGNMENT_CODE")
    private String assignmentCode;

    @Column(name = "ASSIGNMENT_TITLE")
    private String assignmentTitle;

    @Column(name = "PROJECT_ID")
    private String projectId;

    @Column(name = "SUBSCRIPTION_ID")
    private Long subscrptionId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PlacementType placementType;

    @Enumerated(EnumType.STRING)
    private HiringType hiringType;

    @OneToOne(mappedBy = "assignmentEntity", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private WorkLocationEntity workLocationEntity;

    @OneToOne(mappedBy = "assignmentEntity", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private RecruitmentEntity recruitmentEntity;

    @OneToOne(mappedBy = "assignmentEntity", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private BillingEntity billingEntity;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "END_CLIENT")
    private String endClient;

    @OneToMany(mappedBy = "assignmentEntity", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<EmployeeEntity> employeeEntity;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Integer isActive = 1;  // Default to active


}
