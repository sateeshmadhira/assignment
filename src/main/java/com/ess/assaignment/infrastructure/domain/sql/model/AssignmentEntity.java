package com.ess.assaignment.infrastructure.domain.sql.model;

import com.ess.assaignment.core.utils.HiringType;
import com.ess.assaignment.core.utils.PlacementType;
import com.ess.assaignment.core.utils.Status;
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

    @OneToMany(mappedBy = "assignmentEntity", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<EmployeeEntity> employeeEntity;


}
