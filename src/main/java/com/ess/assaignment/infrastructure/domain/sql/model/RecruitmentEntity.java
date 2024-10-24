package com.ess.assaignment.infrastructure.domain.sql.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ASSIGNMENT_RECRUITMENT")
public class RecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "ACCOUNT_MANAGER")
    private String accountManager;

    @Column(name = "JOB_ID")
    private Long jobId;

    @Column(name = "RECRUITMENT_MANAGER")
    private String recruitmentManager;

    @OneToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;
}
